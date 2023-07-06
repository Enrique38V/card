package com.credibanco.card.services;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.dto.CardDTO;

import java.math.BigDecimal;

public interface CardService {

    void saveCard(CardDTO cardDTO);

    void chageStatusCard(String cardId, Short state);

    void balanceTopUp(String cardId, BigDecimal balance);

    BigDecimal checkBalance(String cardId);
}
