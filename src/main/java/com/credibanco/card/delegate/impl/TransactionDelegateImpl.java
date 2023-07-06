package com.credibanco.card.delegate.impl;


import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.delegate.CardDelegate;
import com.credibanco.card.delegate.TransactionDelegate;
import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.entidades.Transaction;
import com.credibanco.card.services.CardService;
import com.credibanco.card.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TransactionDelegateImpl implements TransactionDelegate {

    private final TransactionService transactionService;

    private final CardDelegate cardDelegate;

    private final ObjectMapper objectMapper;

    private final CardService cardService;




    @Override
    public String purchase(String cardId, BigDecimal price){
        try {
            BigDecimal balance = cardService.checkBalance(cardId);
            if (balance.compareTo(price) >= 0) {
                cardDelegate.balanceTo(cardId, price.negate());
                LocalDate currentDate = LocalDate.now();
                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setAmount(price);
                transactionDTO.setCardId(cardId);
                transactionDTO.setTransactionDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                transactionDTO.setStatus(GeneralConstants.ACTIVO);
                transactionService.saveTransaction(transactionDTO);
                return "";
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("response", GeneralConstants.FONDOS_INSUFICIENTES);
                String json = objectMapper.writeValueAsString(response);
                return json;
            }
        }catch(Exception e){
            System.out.println("Error :: " +e);
            return null;
        }

    }

    @Override
    public String getTransactionById(int idTransaction) {
        try {
            TransactionDTO transactionDTO = transactionService.getTransactionById(idTransaction);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String json = objectMapper.writeValueAsString(transactionDTO);
            return json;
        }catch (Exception ex){
            System.out.println("Error :: " +ex);
            return "Error";
        }
    }

    @Override
    public String anulation(String cardId, int transactionId) {
        TransactionDTO transactionDTO= transactionService.getTransactionById(transactionId);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date dayAgo = cal.getTime();
        if(transactionDTO.getTransactionDate().getTime() > dayAgo.getTime()){
            transactionDTO.setStatus(GeneralConstants.ANULADO);
            transactionService.saveTransaction(transactionDTO);
            cardDelegate.balanceTo(cardId, transactionDTO.getAmount());
            return GeneralConstants.PROCESO_EXITOSO;
        }else {
            return GeneralConstants.TIEMPO_EXCEDIDO;
        }

    }

}
