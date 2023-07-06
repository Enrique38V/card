package com.credibanco.card.controller;


import com.credibanco.card.delegate.CardDelegate;
import com.credibanco.card.dto.CardDTO;
import com.credibanco.card.services.CardService;
import com.jayway.jsonpath.internal.JsonFormatter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {


    private final CardDelegate cardDelegate;

    private final CardService cardService;

    private final ModelMapper modelMapper;

    @GetMapping("/{productId}/number")
    public ResponseEntity<?> generateCardNumber(@PathVariable String productId) {
        try {
            System.out.println("Mensaje :: " +productId );
            String cardNumber = cardDelegate.generateNumberCard(productId);
            CardDTO cardDTO = new CardDTO();
            cardDTO.setCardNumber(cardNumber);
            cardDTO.setProductId(productId);
            cardService.saveCard(cardDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println("Error:: " + e);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("enroll")
    public ResponseEntity<?> activateCard(@RequestBody Map<String, Object> request) {
        try {
            String cardId = (String) request.get("cardId");
            cardDelegate.activateCard(cardId);
            ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println("Error:: " + e);
            ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> blockCard(@PathVariable String cardId) {
        try {
            cardDelegate.blockCard(cardId);
            ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println("Error:: " + e);
            ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/balance")
    public ResponseEntity<?> balanceTopUp(@RequestBody Map<String, Object> request){
        try {
            String cardId = (String) request.get("cardId");
            String balanceStr = (String) request.get("balance");
            BigDecimal balance = new BigDecimal(balanceStr);
            cardDelegate.balanceTo(cardId, balance);
            ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println("Error:: " + e);
            ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<?> checkBalance(@PathVariable String cardId){
        try {
            String response = cardDelegate.checkBalance(cardId);
            if(response!= null){
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.internalServerError().build();
        }catch (Exception e){
            System.out.println("Error:: " + e);
            return ResponseEntity.internalServerError().build();
        }
    }


}
