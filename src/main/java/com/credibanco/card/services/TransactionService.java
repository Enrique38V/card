package com.credibanco.card.services;

import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.entidades.Transaction;

public interface TransactionService {

    Transaction saveTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(int transactionId);

}
