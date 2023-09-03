package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository underTest;

    @Autowired
    private AccountRepository accountUnderTest;

    @Test
    void findAllByAccountId() {
        // Arrange
        // Obs.: Já há uma conta no seed do banco de dados
        Account account2 = Factory.creaAccount();
        account2.setInitialDeposit(100.0);
        account2.setAccountNumber("1234");

        Account account3 = Factory.creaAccount();
        account3.setInitialDeposit(100.0);
        account3.setAccountNumber("1234");

        accountUnderTest.save(account2);
        accountUnderTest.save(account3);

        Transaction transaction = Factory.createTransaction();
        transaction.setAccount(account2);
        transaction.setDescription("SAQUE");
        transaction.setValueOfTransaction(200.00);

        Transaction transaction2 = Factory.createTransaction();
        transaction2.setAccount(account2);
        transaction2.setDescription("SAQUE");
        transaction2.setValueOfTransaction(300.00);

        // Act
        underTest.save(transaction);
        underTest.save(transaction2);
        List<Transaction> result = underTest.findAllByAccountId(2L);
        // Assert
        Assertions.assertNotNull(result);
    }
}