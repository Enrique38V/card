package com.credibanco.card.services;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.dto.CardDTO;
import com.credibanco.card.entidades.Card;

import java.math.BigDecimal;

public interface CardService {

    Card saveCard(CardDTO cardDTO);

    int chageStatusCard(String cardId, Short state);

    int balanceTopUp(String cardId, BigDecimal balance);

    BigDecimal checkBalance(String cardId);
}
