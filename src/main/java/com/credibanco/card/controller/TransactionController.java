package com.credibanco.card.controller;


import com.credibanco.card.delegate.TransactionDelegate;
import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionDelegate transactionDelegate;

    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Map<String, Object> request){
        String cardId = (String) request.get("cardId");
        String balanceStr = (String) request.get("price");
        BigDecimal price = new BigDecimal(balanceStr);
        transactionDelegate.purchase(cardId,price);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransaction(@PathVariable String transactionId){
        String json = transactionDelegate.getTransactionById(Integer.parseInt(transactionId));
        if(json!= null){
            return ResponseEntity.ok().body(json);
        }else{
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/anulation")
    public ResponseEntity<?> anulation(@RequestBody Map<String, Object> request){
        String response = transactionDelegate.anulation((String) request.get("cardId"),
                Integer.parseInt((String) request.get("transactionId")));
        return ResponseEntity.ok().body(response);
    }

}
