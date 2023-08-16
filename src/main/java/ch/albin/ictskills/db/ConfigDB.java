package ch.albin.ictskills.db;


import ch.albin.ictskills.model.Person;

import java.util.List;

public class ConfigDB {
    public static String DB_NAME = "DataBinding_Test";
    public static String HOST = "localhost";

    public static String USER = "databinding_user";
    public static String PASSWORD = "123";
    public static List<Class<?>> ANNOTATED_CLASSES = List.of(
        Person.class
    );
}
