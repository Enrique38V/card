package com.credibanco.card.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.credibanco.card.entidades.Card;
import com.credibanco.card.repositories.CardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/credibanco?useSSL=false",
        "spring.datasource.username=root",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
})
public class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;


    @Test
    @Transactional
    void testActiveCard(){
        String cardId = "1234566274136664";
        int state = 1;
        int response = cardRepository.activateCard(cardId, state);
        assertThat(response).isGreaterThan(0);
        assertThat(response).isNotNull();
    }

    @Test
    void testGetCard(){
        String cardId = "1234566274136664";
        Card card = cardRepository.findBycardNumber(cardId);
        assertThat(card).isNotNull();
    }

    @Test
    @Transactional
    void testBalanceTop(){
        String cardId = "1234566274136664";
        BigDecimal balance = new BigDecimal(20000);
        int i = cardRepository.balanceTopUp(cardId,balance);
        assertThat(i).isNotNull();
        assertThat(i).isGreaterThan(0);
    }




}
