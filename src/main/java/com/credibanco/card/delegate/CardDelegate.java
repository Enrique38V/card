package com.credibanco.card.delegate;

import java.math.BigDecimal;

public interface CardDelegate {

    String generateNumberCard(String productId) throws Exception;

    void activateCard(String cardId);

    void blockCard(String cardId);

    String checkBalance(String cardId);

    void balanceTo(String cardId, BigDecimal balance);

}
