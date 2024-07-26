package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.controller.user.loginPages.UserLoginFormController;
import org.example.dao.custom.TransactionDAO;
import org.example.entity.CustomEntity;
import org.example.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public boolean add(Transaction entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Transaction> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();


        Query<Transaction> query = session.createQuery(
                        "FROM Transaction t WHERE t.user.userId = :userId AND t.book.availability = :availability", Transaction.class)
                .setParameter("userId", UserLoginFormController.logUserName)
                .setParameter("availability", false);
        List<Transaction> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(Transaction entity) {
        return false;
    }

    @Override
    public boolean isExists(String id) {
        return false;
    }

    @Override
    public Transaction search(String id) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    public List<CustomEntity> getUserTransaction() {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Query<Object[]> query = session.createQuery(
                "SELECT t.book.bookId, t.book.title, t.book.genre, t.borrowingDate, t.returnDate, t.book.availability " +
                        "FROM Transaction t " + "WHERE t.user.userId=:userId",
                Object[].class
        ).setParameter("userId", UserLoginFormController.logUserName);

        List<Object[]> resultList = query.getResultList();
        List<CustomEntity> customEntities = new ArrayList<>();

        for (Object[] result : resultList) {
            String bookId = (String) result[0];
            String title = (String) result[1];
            String genre = (String) result[2];
            Date borrowingDate = (Date) result[3];
            Date returnDate = (Date) result[4];
            Boolean availability = (Boolean) result[5];

            CustomEntity customEntity = new CustomEntity(bookId, title, genre, borrowingDate, returnDate, availability);
            customEntities.add(customEntity);
        }

        transaction.commit();
        session.close();
        return customEntities;
    }

    public boolean returnBook(String id) {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();

        System.out.println(id);

        Query query = session.createQuery("UPDATE Book SET availability = : availability WHERE bookId = :bookId");
        query.setParameter("availability",true);
        query.setParameter("bookId", id);
        int rowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return rowsUpdated>0;
    }

    public List<CustomEntity> getAllUserTransaction() {
        Session session = FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction = session.beginTransaction();

        Query<Object[]> query = session.createQuery(
                "SELECT t.book.bookId, t.book.title, t.book.genre, t.borrowingDate, t.returnDate, t.book.availability FROM Transaction t ",
                Object[].class);

        List<Object[]> resultList = query.getResultList();
        List<CustomEntity> customEntities = new ArrayList<>();

        for (Object[] result : resultList) {
            String bookId = (String) result[0];
            String title = (String) result[1];
            String genre = (String) result[2];
            Date borrowingDate = (Date) result[3];
            Date returnDate = (Date) result[4];
            Boolean availability = (Boolean) result[5];

            CustomEntity customEntity = new CustomEntity(bookId, title, genre, borrowingDate, returnDate, availability);
            customEntities.add(customEntity);
        }

        transaction.commit();
        session.close();
        return customEntities;
    }

    @Override
    public int generateNextTransactionId() {
        Session session= FactoryConfiguration.getInstance().getSession();
        org.hibernate.Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT MAX(CAST(transactionId AS integer)) FROM Transaction ");
        Integer maxId = (Integer) query.uniqueResult();

        if (maxId != null) {
            return splitTransactionId(maxId);
        }

        transaction.commit();
        session.close();

        return splitTransactionId(0);
    }

    private static int splitTransactionId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }

}
