package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.UserDAO;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean add(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<User> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query<User> query = session.createQuery("FROM User", User.class);
        List<User> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean isExists(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user= session.get(User.class,id);
        transaction.commit();
        session.close();

        return user != null;
    }

    @Override
    public User search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE userId=:id",User.class);
        query.setParameter("id",id);

        User user = query.getSingleResult();

        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user=session.get(User.class,id);

        if (user!=null){
            session.delete(user);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public int generateNextUserId() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT MAX(CAST(userId AS integer)) FROM User ");
        Integer maxId = (Integer) query.uniqueResult();

        transaction.commit();
        session.close();

        if (maxId != null) {
            return splitUserId(maxId);
        }

        transaction.commit();
        session.close();

        return splitUserId(0);
    }

    private static int splitUserId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }
}
