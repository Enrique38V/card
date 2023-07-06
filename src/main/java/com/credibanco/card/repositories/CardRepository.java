package com.credibanco.card.repositories;

import com.credibanco.card.entidades.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Modifying
    @Query(value = " UPDATE cards set state = :state where card_number = :cardId ", nativeQuery = true)
    int activateCard(@Param("cardId") String cardId, @Param("state") int state);

    Card findBycardNumber(String cardId);

    @Modifying
    @Query(value = "UPDATE cards SET balance = :balance WHERE card_number = :cardId", nativeQuery = true)
    void balanceTopUp(@Param("cardId") String cardId, @Param("balance")BigDecimal balance);

}
