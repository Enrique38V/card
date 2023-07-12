package com.credibanco.card.services.impl;

import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.entidades.Transaction;
import com.credibanco.card.repositories.TransactionRepository;
import com.credibanco.card.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private  TransactionRepository transactionRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private  ObjectMapper objectMapper;

    @Override
    public Transaction saveTransaction(TransactionDTO transactionDTO){
        try {
            Transaction trans = modelHelperToEntity(transactionDTO);
            return transactionRepository.save(trans);
        }catch (Exception ex){
            System.out.println("Error :" + ex);
            return null;
        }
    }

    private Transaction modelHelperToEntity(TransactionDTO transactionDTO){
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setId(transactionDTO.getId());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCardId(transactionDTO.getCardId());
        return transaction;
    }


    @Override
    public TransactionDTO getTransactionById(int idTransaction){
        try {
            Transaction trans = transactionRepository.findById(idTransaction);
            TransactionDTO transactionDTO = modelHelperToDTO(trans);
            return transactionDTO;
        }catch (Exception ex){
            System.out.println("Error :: " + ex);
            return null;
        }
    }

    private TransactionDTO modelHelperToDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setCardId(transaction.getCardId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setId(transaction.getId());
        return transactionDTO;
    }


}
