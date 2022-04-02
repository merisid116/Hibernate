package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Properties;


public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/new_schema";
    private static final String NAME = "root";
    private static final String PASSWORD = "1357";
    private static SessionFactory sessionFactory;

    public static SessionFactory getConnection() {

        try {
            Properties prop = new Properties();
                    prop.setProperty("hibernate.connection.driver_class", DRIVER);
                    prop.setProperty("hibernate.connection.url", URL);
                    prop.setProperty("hibernate.connection.username", NAME);
                    prop.setProperty("hibernate.connection.password", PASSWORD);
                    prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                    prop.setProperty("hibernate.hbm2ddl.auto", "update");

                  Configuration configuration = new Configuration();
                    configuration.setProperties(prop);
                    configuration.addAnnotatedClass(User.class);
                    sessionFactory = configuration.buildSessionFactory();


        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
