package ch.albin.ictskills.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MySqlDB {
    private static MySqlDB instance = null;

    private Configuration configuration = null;
    private SessionFactory sessionFactory = null;

    private MySqlDB() {
    }

    public static MySqlDB getInstance() {
        if (instance == null) {
            instance = new MySqlDB();
        }
        return instance;
    }

    public Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();

            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://" + ConfigDB.HOST + "/" + ConfigDB.DB_NAME);
            configuration.setProperty("hibernate.connection.username", ConfigDB.USER);
            configuration.setProperty("hibernate.connection.password", ConfigDB.PASSWORD);
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");

            ConfigDB.ANNOTATED_CLASSES.forEach(configuration::addAnnotatedClass);
        }
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            setSessionFactory(getConfiguration().buildSessionFactory());
        }
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void closeSql() {
        if (getSessionFactory() != null || getSessionFactory().isOpen()) {
            sessionFactory.close();
        }
    }
}
