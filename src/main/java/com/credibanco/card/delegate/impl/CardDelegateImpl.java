package com.credibanco.card.delegate.impl;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.delegate.CardDelegate;
import com.credibanco.card.services.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class CardDelegateImpl implements CardDelegate {

    private final CardService cardService;

    private final ObjectMapper objectMapper;

    @Override
    public String generateNumberCard(String prodcutId){
        Random r = new Random();
        Long numbers = r.nextLong() % 10000000000L;
        return prodcutId + String.format("%010d", Math.abs(numbers));
    }

    @Override
    public void activateCard(String cardId){
        cardService.chageStatusCard(cardId, GeneralConstants.ACTIVO);
    }

    @Override
    public void blockCard(String cardId){
        cardService.chageStatusCard(cardId, GeneralConstants.BLOQUEADO);
    }

    @Override
    public String checkBalance(String cardId){
        try {
            BigDecimal balance = cardService.checkBalance(cardId);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> response = new HashMap<>();
            response.put("balance", balance);
            String json = objectMapper.writeValueAsString(response);
            return json;
        }catch (Exception e){
            System.out.println("Error :: " +e);
            return null;
        }
    }

    @Override
    public void balanceTo(String cardId, BigDecimal balance){
        BigDecimal newBalance = cardService.checkBalance(cardId);
        newBalance = balance.add(newBalance);
        cardService.balanceTopUp(cardId, newBalance);
    }




}
