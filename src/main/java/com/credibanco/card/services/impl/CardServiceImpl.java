package com.credibanco.card.services.impl;

import com.credibanco.card.constants.GeneralConstants;
import com.credibanco.card.dto.CardDTO;
import com.credibanco.card.entidades.Card;
import com.credibanco.card.repositories.CardRepository;
import com.credibanco.card.services.CardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final ModelMapper modelMapper;

    private final CardRepository cardRepository;

    @Override
    public void saveCard(CardDTO cardDTO) {
        cardDTO.setBalance(BigDecimal.ZERO);
        Calendar cal = Calendar.getInstance();
        cardDTO.setExpirationDate(
                (cal.get(Calendar.MONTH)+1)+"/"+(cal.get(Calendar.YEAR)+3));
        cardDTO.setCardHolderName("");
        cardDTO.setState(GeneralConstants.ACTIVO);
        Card card = modelMapper.map(cardDTO, Card.class);
        cardRepository.save(card);
    }


    @Transactional
    @Override
    public void chageStatusCard(String cardId, Short status) {
        try{
           cardRepository.activateCard(cardId, status);
        }catch (Exception e){
            System.out.println("Error :: " +e );
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
    public void balanceTopUp(String cardId, BigDecimal balance){
        cardRepository.balanceTopUp(cardId, balance);
    }

}
