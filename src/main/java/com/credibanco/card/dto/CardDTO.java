package com.credibanco.card.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardDTO {

    private Long id;
    private String CardNumber;
    private String productId;
    private String cardHolderName;
    private String expirationDate;
    private int state;
    private BigDecimal balance;

}
