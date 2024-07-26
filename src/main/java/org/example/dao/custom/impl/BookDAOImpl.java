package org.example.dao.custom.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.custom.BookDAO;
import org.example.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public boolean add(Book entity) {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Book> getAll() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query<Book> query = session.createQuery("FROM Book", Book.class);
        List<Book> resultList = query.getResultList();

        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public boolean update(Book entity) {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean isExists(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Book book= session.get(Book.class,id);
        transaction.commit();
        session.close();

        return book != null;
    }

    @Override
    public Book search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Book> query = session.createQuery("FROM Book WHERE bookId=:id",Book.class);
        query.setParameter("id",id);

        Book book = query.getSingleResult();

        transaction.commit();
        session.close();

        return book;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Book book = session.get(Book.class, id);

        if (book!=null){
            session.delete(book);
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }


    @Override
    public boolean borrowBook(String id) {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("UPDATE Book SET availability = : availability WHERE bookId = :bookId");
        query.setParameter("availability",false);
        query.setParameter("bookId", id);
        int rowsUpdated = query.executeUpdate();
        transaction.commit();
        session.close();
        return rowsUpdated>0;
    }

    @Override
    public int generateNextBookId() {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT MAX(CAST(bookId AS integer)) FROM Book");
        Integer maxId = (Integer) query.uniqueResult();

        transaction.commit();
        session.close();

        if (maxId != null) {
            return splitBookId(maxId);
        }

        transaction.commit();
        session.close();

        return splitBookId(0);
    }


    private static int splitBookId(int id) {
        if (id ==0){
            return 1;
        }
        return ++id;
    }
}
