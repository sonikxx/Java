package edu.school21.app;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;
import java.util.Set;

public class Program {
    private static String PACKAGE_NAME = "edu.school21.classes";
    private static String SEPARATOR = "---------------------";
    private static Scanner in = new Scanner(System.in);
    private static Set<Class<?>> allClasses;
    private static Class<?> inputClass;
    private static  Field[] fields;
    private static Method[] methods;
    private static Constructor noParamConstructor;
    private static Object newObj;

    public static void main(String[] args) {
        try {
            getClasses();
            getInputClass();
            System.out.println(SEPARATOR);
            printInfo();
            createObject();
            useMethod();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static void getClasses() {
        Reflections reflections = new Reflections(PACKAGE_NAME, new SubTypesScanner(false));
        allClasses = reflections.getSubTypesOf(Object.class);
        System.out.println("Classes:");
        for (Class<?> clazz : allClasses) {
            System.out.println("  - " + clazz.getSimpleName());
        }
        System.out.println(SEPARATOR);
    }

    public static void getInputClass() {
        System.out.println("Enter class name:");
        System.out.print("-> ");
        String input = in.nextLine();
        Class<?> res = null;
        for (Class<?> clazz : allClasses) {
            if (clazz.getSimpleName().equals(input))
                res = clazz;
        }
        if (res == null) {
            System.out.println("Invalid input, try again :(");
            getInputClass();
        } else {
            inputClass = res;
        }
    }

    private static void printInfo() {
        System.out.println("fields:");
        fields = inputClass.getDeclaredFields();
        for (Field field: fields) {
            System.out.println("\t    " + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println("methods:");
        methods = inputClass.getDeclaredMethods();
        for (Method method: methods) {
            System.out.print("\t    ");
            if (!method.getReturnType().getSimpleName().equals("void"))
                System.out.print(method.getReturnType().getSimpleName() + " ");
            System.out.print(method.getName());
            Class<?>[] args = method.getParameterTypes();
            if (args.length > 0) {
                System.out.print("(");
                boolean isFirst = true;
                for (Class<?> clazz: args) {
                    if (!isFirst)
                        System.out.print(", ");
                    isFirst = false;
                    System.out.print(clazz.getSimpleName());
                }
                System.out.print(")");
            } else {
                System.out.print("()");
            }
            System.out.println();
        }
        System.out.println(SEPARATOR);
    }

    private static void createObject() {
        System.out.println("Let’s create an object.");
        Constructor<?>[] constructors = inputClass.getDeclaredConstructors();
        Constructor selectConstructor = constructors[0];
        boolean isParam = false;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() > 0) {
                selectConstructor = constructor;
                isParam = true;
                break;
            }
        }
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0 && !isParam) {
                selectConstructor = constructor;
                break;
            } else if (constructor.getParameterCount() == 0 && isParam) {
                noParamConstructor = constructor;
            }
        }
        if (isParam) {
            Parameter[] parameters = selectConstructor.getParameters();
            Object[] initArgs = new Object[parameters.length];
            int i = 0;
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName() + ":");
                System.out.print("-> ");
                String input = in.nextLine();
                if (parameter.getType() == String.class)
                    initArgs[i] = input;
                else if (parameter.getType() == int.class || parameter.getType() == Integer.class)
                    initArgs[i] = Integer.parseInt(input);
                else if (parameter.getType() == double.class || parameter.getType() == Double.class)
                    initArgs[i] = Double.parseDouble(input);
                else if (parameter.getType() == long.class || parameter.getType() == Long.class)
                    initArgs[i] = Long.parseLong(input);
                else if (parameter.getType() == boolean.class || parameter.getType() == Boolean.class)
                    initArgs[i] = Boolean.parseBoolean(input);
                else
                    throw new RuntimeException("Incorrect type :(");
                ++i;
            }
            try {
                newObj = selectConstructor.newInstance(initArgs);
                System.out.println("Object created: " + newObj);
            } catch (Exception e) {
                System.err.println("Error :" + e.getMessage());
                System.exit(-1);
            }
        } else {
            try {
                newObj = selectConstructor.newInstance();
                System.out.println("Object created: " + newObj);
            } catch (Exception e) {
                System.err.println("Error :" + e.getMessage());
                System.exit(-1);
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void useMethod() {
        System.out.println("Enter name of the method for call:");
        System.out.print("-> ");
        String input = in.nextLine().trim();
        boolean findMethod = false;
        for (Method method: methods) {
            StringBuilder func = new StringBuilder(method.getName());
            Class<?>[] args = method.getParameterTypes();
            if (args.length > 0) {
                func.append("(");
                boolean isFirst = true;
                for (Class<?> clazz: args) {
                    if (!isFirst)
                        func.append(", ");
                    isFirst = false;
                    func.append(clazz.getSimpleName());
                }
                func.append(")");
            } else {
                func.append("()");
            }
            String funcStr = func.toString();
            if (funcStr.equals(input)) {
                ExecuteMethod(method);
                findMethod = true;
                break;
            }
        }
        if (!findMethod)
            throw new RuntimeException("Incorrect method :(");
    }

    private static void ExecuteMethod(Method method) {
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] argsToPass = new Object[parameterTypes.length];
            int i = 0;
            for (Class<?> parameter: parameterTypes) {
                System.out.println("Enter " + parameter.getTypeName() + " value:");
                System.out.print("-> ");
                String input = in.nextLine();
                switch(parameter.getTypeName()) {
                    case "String":
                        argsToPass[i] = input;
                        break;
                    case "int":
                    case "Integer":
                        argsToPass[i] = Integer.parseInt(input);
                        break;
                    case "double":
                    case "Double":
                        argsToPass[i] = Double.parseDouble(input);
                        break;
                    case "long":
                    case "Long":
                        argsToPass[i] = Long.parseLong(input);
                        break;
                    case "boolean":
                    case "Boolean":
                        argsToPass[i] = Boolean.parseBoolean(input);
                        break;
                    default:
                        throw new RuntimeException("Incorrect type :(");
                }
                ++i;
            }
            Object result = method.invoke(newObj, argsToPass);
            if (!method.getReturnType().getSimpleName().equals("void")) {
                System.out.println("Method returned:");
                System.out.println(result);
            }
        } catch (Exception e) {
            System.err.println("Error :" + e.getMessage());
            System.exit(-1);
        }
    }
}
