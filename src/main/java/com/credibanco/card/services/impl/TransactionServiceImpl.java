package com.credibanco.card.services.impl;

import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.entidades.Transaction;
import com.credibanco.card.repositories.TransactionRepository;
import com.credibanco.card.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    @Override
    public void saveTransaction(TransactionDTO transactionDTO){
        try {
            Transaction trans = modelMapper.map(transactionDTO, Transaction.class);
            transactionRepository.save(trans);
        }catch (Exception ex){
            System.out.println("Error :" + ex);
        }
    }


    @Override
    public TransactionDTO getTransactionById(int idTransaction){
        try {
            Transaction trans = transactionRepository.findById(idTransaction);
            TransactionDTO transactionDTO = modelMapper.map(trans, TransactionDTO.class);
            return transactionDTO;
        }catch (Exception ex){
            System.out.println("Error :: " + ex);
            return null;
        }
    }


}
