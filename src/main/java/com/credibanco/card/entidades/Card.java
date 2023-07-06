package com.credibanco.card.entidades;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Column(name = "state")
    private int state;

    @Column(name = "balance")
    private BigDecimal balance;

}
