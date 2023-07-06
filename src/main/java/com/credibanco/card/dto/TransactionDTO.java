package com.credibanco.card.dto;

import com.credibanco.card.entidades.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class TransactionDTO {

    private int id;
    private String cardId;
    private BigDecimal amount;
    private Date transactionDate;
    private int status;

}
