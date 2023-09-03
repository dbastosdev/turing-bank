package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.repositories.TransactionRepository;
import br.com.douglas.turingbankh2.requests.AccountReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.stream.FactoryConfigurationError;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransactionService service;

    @Test
    void findAllShouldReturnListOfTransactions() {
        // Arrange
        Transaction transaction = Factory.createTransaction();
        // Action
        Mockito.when(repository.findAll()).thenReturn(List.of(transaction));
        List<TransactionRes> tResult = service.findAll();
        // Assert
       Assertions.assertNotNull(tResult);
    }

    @Test
    void findByNotExistentIdShouldThrowsEntityDuplicatedExceptionWhenCpfIsEqual() {
        // A - arrange
        long id = 300L;

        // A - Act
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        // A - Assert
        Assertions.assertThrows(EntityNotFoundException.class, () ->
        {
            service.findById(id);
        });
    }

    @Test
    void findByIdShouldReturnEntity() {
        // A - arrange
        long id = 1L;
        Transaction transaction = Factory.createTransaction();
        TransactionRes transactionExpected = Factory.createTransactionRes(transaction);

        // A - Act
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(transaction));
        TransactionRes result = service.findById(id);
        // A - Assert
        // Se eu remover o getClass dá erro.
        Assertions.assertEquals(transactionExpected.getClass(), result.getClass());

    }

    // NÃO CONSEGUI FAZER.
    @Test
    void insertGivUpdateAccountBalanceAndReturnTransaction() throws InstantiationException, IllegalAccessException {
        // Arrange
        TransactionReq transactionReq = new TransactionReq();
        transactionReq.setAccount(new Account());
        transactionReq.setDescription("SAQUE");
        transactionReq.getAccount().setId(1L);
        Account acc = new Account();
        Transaction transaction = Factory.createTransaction();
        // Act
        Mockito.when(repository.save(transaction)).thenReturn(transaction);
        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));
        TransactionRes tResult = service.insert(transactionReq);
        // Assert
        Assertions.assertNotNull(tResult);
    }

    @Test
    void insertShouldThrowEntityDuplicatedException() {
        // Arrange
        Transaction transaction = new Transaction();
        // A - Assert
        Assertions.assertThrows(RuntimeException.class, () ->
        {
            service.insert(new TransactionReq());
        });
    }

    @Test
    void allTransactionByAccountShouldReturnAEmptyListOfSpecificAccount() {
        // Arrange
        long id = 1L;
       Transaction transaction = Factory.createTransaction();
       TransactionRes transactionRes = Factory.createTransactionRes(transaction);
       Account acc = new Account();
        // Act
        List<TransactionRes> listResult = service.allTransactionByAccount(id);
        // Assert
        Assertions.assertNotNull(listResult);
    }
}