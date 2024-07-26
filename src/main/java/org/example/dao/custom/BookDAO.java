package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Book;

public interface BookDAO extends CrudDAO<Book> {
    boolean borrowBook(String id);

    int generateNextBookId();
}
