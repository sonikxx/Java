package edu.school21.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.Set;

public class OrmManager {
    private Connection connection;
    String tableName;

     public OrmManager(HikariDataSource dataSource) {
         try {
             this.connection = dataSource.getConnection();
             this.createTable();
         } catch (SQLException e) {
             System.err.println("Error: " + e.getMessage());
             System.exit(-1);
         }
     }

    public void save(Object entity) {
         if (entity == null)
             throw new RuntimeException("You cannot insert null");
        Class<?> entityClass = entity.getClass();
        if (entityClass.getAnnotation(OrmEntity.class) == null)
            throw new RuntimeException("Invalid insert");
        String queryInsert = "INSERT INTO " + this.tableName + "(";
        StringBuilder columnInsert = new StringBuilder();
        StringBuilder valueInsert = new StringBuilder();
        Field[] fields = entityClass.getDeclaredFields();
        boolean isFirst = true;
        for (Field field: fields) {
            if (field.getAnnotation(OrmColumn.class) != null) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                if (!isFirst) {
                    columnInsert.append(", ");
                    valueInsert.append(", ");
                }
                isFirst = false;
                columnInsert.append(ormColumn.name());
                try {
                    field.setAccessible(true); // делаем поле доступным, если оно private
                    Object value = field.get(entity);
                    if (field.getType().getSimpleName().equals("String") && value != null)
                        valueInsert.append("'").append(value).append("'");
                    else
                        valueInsert.append(value);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    System.err.println("Error: " + e.getMessage());
                    System.exit(-1);
                }
            }
        }
        queryInsert += columnInsert + ") VALUES (" + valueInsert + ");";
        try {
            Statement statementInsert = this.connection.createStatement();
            statementInsert.execute(queryInsert);
            System.out.println(queryInsert);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void update(Object entity) {
        if (entity == null)
            throw new RuntimeException("You cannot update null");
        Class<?> entityClass = entity.getClass();
        if (entityClass.getAnnotation(OrmEntity.class) == null)
            throw new RuntimeException("Invalid insert");
        StringBuilder queryUpdate = new StringBuilder("UPDATE " + this.tableName + " SET ");
        Field[] fields = entityClass.getDeclaredFields();
        Object idEntity = null;
        boolean isFirst = true;
        for (Field field: fields) {
            try {
                if (!isFirst)
                    queryUpdate.append(", ");
                field.setAccessible(true);
                if (field.getAnnotation(OrmColumn.class) != null) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    queryUpdate.append(ormColumn.name()).append(" = ");
                    if (field.getType().getSimpleName().equals("String")) {
                        queryUpdate.append("'").append(field.get(entity)).append("'");
                    } else
                        queryUpdate.append(field.get(entity));
                    isFirst = false;
                } else if (field.getAnnotation(OrmColumnId.class) != null)
                    idEntity = field.get(entity);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(-1);
            }
        }
        queryUpdate.append(" WHERE id = ").append(idEntity).append(";");
        try {
            Statement statementUpdate = connection.createStatement();
            statementUpdate.execute(queryUpdate.toString());
            System.out.println(queryUpdate);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }

    }

    public <T> T findById(Long id, Class<T> aClass) {
         if (aClass.getAnnotation(OrmEntity.class) == null)
             throw new RuntimeException("You cannot find this elem");
         String querySelect = "SELECT * FROM " + this.tableName + " WHERE id = " + id + ";";
         try {
             Statement statementSelect = connection.createStatement();
             ResultSet resultSelect = statementSelect.executeQuery(querySelect);
             System.out.println(querySelect);
             if (!resultSelect.next()) {
                 System.out.println("Object with id = " + id + " not found");
                 return null;
             }
             Constructor<?>[] constructors = aClass.getDeclaredConstructors();
             Constructor<?> selectConstructor = constructors[0];
             for (Constructor<?> constructor : constructors) {
                 if (constructor.getParameters().length == aClass.getDeclaredFields().length) {
                     selectConstructor = constructor;
                     break;
                 }
             }
             Parameter[] parameters = selectConstructor.getParameters();
             Field[] fields = aClass.getDeclaredFields();
             Object[] initArgs = new Object[parameters.length];
             int i = 0;
             for (Field field: fields) {
                 if (field.getAnnotation(OrmColumn.class) != null) {
                     OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                     initArgs[i] = resultSelect.getObject(ormColumn.name());
                 } else if (field.getAnnotation(OrmColumnId.class) != null)
                     initArgs[i] = resultSelect.getLong(1);
                 ++i;
             }
             try {
                 T newObj = (T) selectConstructor.newInstance(initArgs);
                 return newObj;
             } catch (Exception e) {
                 System.err.println("Error: " + e.getMessage());
                 System.exit(-1);
             }
         } catch (SQLException e) {
             System.err.println("Error: " + e.getMessage());
             System.exit(-1);
         }
         return null;
    }

     public void createTable() {
         Reflections reflections = new Reflections("edu.school21.models", new TypeAnnotationsScanner());
         Set<Class<?>> elementsEntity = reflections.getTypesAnnotatedWith(OrmEntity.class);
             for (Class<?> elem : elementsEntity) {
                 this.tableName = elem.getAnnotation(OrmEntity.class).table();
                 this.dropTable(elem);
                 StringBuilder queryCreate = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (\n");
                 Field[] fields = elem.getDeclaredFields();
                 boolean isFirst = true;
                 for (Field field : fields) {
                     if (!isFirst)
                         queryCreate.append(",\n");
                     isFirst = false;
                     if (field.getAnnotation(OrmColumn.class) != null) {
                         OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                         try {
                             queryCreate.append(ormColumn.name()).append(" ").append(getType(field));
                         } catch (RuntimeException e) {
                             System.err.println("Error: " + e.getMessage());
                             System.exit(-1);
                         }
                     } else if (field.getAnnotation(OrmColumnId.class) != null)
                         queryCreate.append("id SERIAL PRIMARY KEY");
                 }
                 queryCreate.append("\n);");
                 try {
                     Statement statementCreate = this.connection.createStatement();
                     statementCreate.execute(queryCreate.toString());
                     System.out.println(queryCreate);
                 } catch (SQLException e) {
                     System.err.println("Error: " + e.getMessage());
                     System.exit(-1);
                 }
             }
     }


     private void dropTable(Class<?> elem) {
         String queryDrop = "DROP TABLE IF EXISTS " + this.tableName + " CASCADE;";
         try {
             Statement statementDrop = this.connection.createStatement();
             statementDrop.execute(queryDrop);
             System.out.println(queryDrop);
         } catch (SQLException e) {
             System.err.println("Error: " + e.getMessage());
             System.exit(-1);
         }
     }

     private static String getType(Field field) {
         OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
         String fieldType = field.getType().getSimpleName();
         if (fieldType.equals("String"))
             return "VARCHAR(" + ormColumn.length() + ")";
         else if (fieldType.equals("Integer"))
             return "INT";
         else if (fieldType.equals("Double"))
             return "NUMERIC";
         else if (fieldType.equals("Boolean"))
             return "BOOLEAN";
         else if (fieldType.equals("Long"))
             return "BIGINT";
         else
             throw new RuntimeException("Unknown type field");
     }

}
