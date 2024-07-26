package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.CategoryDAO;
import org.example.entity.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public boolean add(Category entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Category> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query<Category> query = session.createQuery("FROM Category ", Category.class);
        List<Category> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(Category entity) {
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

        Category category= session.get(Category.class,id);
        transaction.commit();
        session.close();

        return category != null;
    }

    @Override
    public Category search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Category> query = session.createQuery("FROM Category WHERE categoryId =:id",Category.class);
        query.setParameter("categoryId",id);

        Category category = query.getSingleResult();

        transaction.commit();
        session.close();

        return category;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Category category=session.get(Category.class,id);

        if (category !=null){
            session.delete(category);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public int generateNextCategoryId() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT MAX(CAST(categoryId AS integer)) FROM Category ");
        Integer maxId = (Integer) query.uniqueResult();

        transaction.commit();
        session.close();

        if (maxId != null) {
            return splitCategoryId(maxId);
        }

        return splitCategoryId(0);
    }
    private static int splitCategoryId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }
}
