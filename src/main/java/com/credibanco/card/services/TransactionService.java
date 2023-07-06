package com.credibanco.card.services;

import com.credibanco.card.dto.TransactionDTO;

public interface TransactionService {

    void saveTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(int transactionId);

}
