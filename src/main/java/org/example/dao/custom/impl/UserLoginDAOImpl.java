package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.UserLoginDAO;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserLoginDAOImpl implements UserLoginDAO {
    @Override
    public boolean login(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE userName = :username AND password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        User user = query.uniqueResult();

        if (user!=null) {
            return true;
        }

        transaction.commit();
        session.close();

        return false;
    }

    @Override
    public String getUserId(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE userName = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();

        if (user!=null){
            return user.getUserId();
        }

        transaction.commit();
        session.close();

        return null;
    }
}
