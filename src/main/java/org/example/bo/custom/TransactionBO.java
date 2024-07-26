package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.TransactionDto;
import org.example.dto.UserTransactionDto;

import java.util.List;

public interface TransactionBO extends SuperBO {
    boolean saveTransaction(TransactionDto dto);

    List<TransactionDto> getTransactions();

    List<UserTransactionDto> getUserTransactions();

    boolean returnBook(String id);

    List<UserTransactionDto> getAllUserTransactions();

    int generateNextTransactionId();

}
