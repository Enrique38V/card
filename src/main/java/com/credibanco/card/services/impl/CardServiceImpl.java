package com.credibanco.card.services.impl;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.dto.CardDTO;
import com.credibanco.card.entidades.Card;
import com.credibanco.card.repositories.CardRepository;
import com.credibanco.card.services.CardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card saveCard(CardDTO cardDTO) {
        cardDTO.setBalance(BigDecimal.ZERO);
        Calendar cal = Calendar.getInstance();
        cardDTO.setExpirationDate(
                (cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.YEAR)+3));
        cardDTO.setCardHolderName("");
        cardDTO.setState(GeneralConstants.ACTIVO);
        Card card = modelHelper(cardDTO);
        return cardRepository.save(card);
    }

    private Card modelHelper(CardDTO cardDTO){
       Card card = new Card();
       card.setId(cardDTO.getId());
       card.setCardNumber(cardDTO.getCardNumber());
       card.setProductId(cardDTO.getProductId());
       card.setExpirationDate(cardDTO.getExpirationDate());
       card.setCardholderName(cardDTO.getCardHolderName());
       card.setBalance(cardDTO.getBalance());
       return card;
    }


    @Transactional
    @Override
    public int chageStatusCard(String cardId, Short status) {
        try{
           return cardRepository.activateCard(cardId, status);
        }catch (Exception e){
            System.out.println("Error :: " +e );
            return 0;
        }
    }

    @Transactional
    @Override
    public BigDecimal checkBalance(String cardId){
        try{
            Card card = cardRepository.findBycardNumber(cardId);
            return card.getBalance();
        }catch (Exception e){

        }
        return BigDecimal.ZERO;
    }

    @Override
    @Transactional
    public int balanceTopUp(String cardId, BigDecimal balance){
        return cardRepository.balanceTopUp(cardId, balance);
    }



}
