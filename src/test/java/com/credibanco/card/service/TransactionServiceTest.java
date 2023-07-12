package com.credibanco.card.service;

import com.credibanco.card.dto.TransactionDTO;
import com.credibanco.card.entidades.Transaction;
import com.credibanco.card.repositories.TransactionRepository;
import com.credibanco.card.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTransaction(){
        // Arrange
        TransactionDTO transactionDTO = new TransactionDTO();
        LocalDate currentDate = LocalDate.now();
        transactionDTO.setTransactionDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        transactionDTO.setCardId("1000210302");
        transactionDTO.setStatus(1);
        // Configurar los valores del transactionDTO
        Transaction transaction = new Transaction();
        // Configurar los valores del transaction
        Mockito.doReturn(transaction).when(transactionRepository).save(Mockito.any(Transaction.class)); // Configurar el mock utilizando doReturn()
        // Act
        Transaction response = transactionService.saveTransaction(transactionDTO);
        // Assert
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(transaction);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }

    @Test
    public void testGetTransactionById(){
        int idTransaction  = 1;
        Transaction transaction = new Transaction();
        Mockito.when(transactionRepository.findById((idTransaction))).thenReturn(transaction);
        TransactionDTO transactionDTO = transactionService.getTransactionById(idTransaction);
        assertThat(transactionDTO).isNotNull();
    }



}
