package org.example.config;

import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfiguration {
    public static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration(){

        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("/hibernate.properties"));
        } catch (Exception ignored) {

        }

        Configuration configuration = new Configuration().mergeProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Branch.class)
                .addAnnotatedClass(Transaction.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Category.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
        if (factoryConfiguration==null){
            factoryConfiguration=new FactoryConfiguration();
        }else {
            return factoryConfiguration;
        }
        return factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
