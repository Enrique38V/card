package com.credibanco.card.delegate;

import java.math.BigDecimal;

public interface TransactionDelegate {

    String purchase(String cardId, BigDecimal price);

    String anulation(String cardId, int transactionId);

    String getTransactionById(int idTransaction);


}
