package com.credibanco.card.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.delegate.CardDelegate;
import com.credibanco.card.delegate.impl.CardDelegateImpl;
import com.credibanco.card.dto.CardDTO;
import com.credibanco.card.entidades.Card;
import com.credibanco.card.repositories.CardRepository;
import com.credibanco.card.services.impl.CardServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Calendar;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/credibanco?useSSL=false",
        "spring.datasource.username=root",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
})
public class CardServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardDelegateImpl cardDelegate;

    @InjectMocks
    private CardServiceImpl cardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCard() {
        // Arrange
        CardDTO cardDTO = new CardDTO();
        // Configurar los campos de cardDTO según sea necesario
        Card card = new Card();


        cardDTO.setProductId("625123");
        Calendar cal = Calendar.getInstance();
        cardDTO.setExpirationDate((cal.get(Calendar.MONTH) + 1) + "/" + (cal.get(Calendar.YEAR) + 3));
        cardDTO.setCardHolderName("");
        cardDTO.setState(GeneralConstants.ACTIVO);
        cardDTO.setCardNumber(cardDelegate.generateNumberCard("625123"));
        card.setCardNumber(cardDTO.getCardNumber());
        card.setProductId(cardDTO.getProductId());
        card.setState(cardDTO.getState());
        card.setExpirationDate(cardDTO.getExpirationDate());
        Mockito.when(cardRepository.save(Mockito.any(Card.class))).thenReturn(card);
        // Act
        Card result = cardService.saveCard(cardDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(card);
        Mockito.verify(cardRepository, Mockito.times(1)).save(Mockito.any(Card.class));
    }

    @Test
    public void testChageStatusCard() {
        // Arrange
        String cardId = "1234579037972315";
        Short status = GeneralConstants.ACTIVO;
        Mockito.when(cardRepository.activateCard(cardId, status)).thenReturn(1);

        // Act
        int result = cardService.chageStatusCard(cardId, status);

        // Assert
        assertThat(result).isGreaterThan(0);
        Mockito.verify(cardRepository, Mockito.times(1)).activateCard(cardId, status);
    }

    @Test
    public void testCheckBalance() {
        // Arrange
        String cardId = "1234579037972315";
        BigDecimal balance = BigDecimal.valueOf(5000);
        Card card = new Card();
        card.setBalance(balance);
        Mockito.when(cardRepository.findBycardNumber(cardId)).thenReturn(card);
        // Act
        BigDecimal result = cardService.checkBalance(cardId);
        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(balance);
        Mockito.verify(cardRepository, Mockito.times(1)).findBycardNumber(cardId);
    }

    @Test
    public void testBalanceTopUp() {
        // Arrange
        String cardId = "1234579037972315";
        BigDecimal balance = BigDecimal.valueOf(20000);
        Mockito.when(cardRepository.balanceTopUp(cardId, balance)).thenReturn(1);
        // Act
        int result = cardService.balanceTopUp(cardId, balance);
        // Assert
        assertThat(result).isEqualTo(1);
        Mockito.verify(cardRepository, Mockito.times(1)).balanceTopUp(cardId, balance);
    }


}
