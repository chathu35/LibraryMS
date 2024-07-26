package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.CustomEntity;
import org.example.entity.Transaction;

import java.util.List;

public interface TransactionDAO extends CrudDAO<Transaction> {
    List<CustomEntity> getUserTransaction();

    boolean returnBook(String id);

    List<CustomEntity> getAllUserTransaction();

    int generateNextTransactionId();
}
