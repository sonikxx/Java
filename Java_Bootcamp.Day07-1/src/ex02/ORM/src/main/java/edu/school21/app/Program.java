package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.models.User;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ORM");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(config); // пул соединений HikariCP
        OrmManager manager = new OrmManager(dataSource);
        User user1 = new User(1L, "User1", "User1LastName", 10);
        User user2 = new User(2L, "User2", "User2LastName", 20);
        User user3 = new User(3L, null, "UserNullLastName", null);
        User user4 = new User(4L, "User4", "User4LastName", 30);
        try {
            manager.save(user1);
            manager.save(user2);
            manager.save(user3);
            System.out.println(manager.findById(user2.getId(), user2.getClass()));
            System.out.println(manager.findById(user4.getId(), user4.getClass()));
            user3.setFirstName("User3");
            user3.setLastName("User3LastName");
            manager.update(user1);
            manager.update(user3);
            manager.update(user4);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }

    }
}
