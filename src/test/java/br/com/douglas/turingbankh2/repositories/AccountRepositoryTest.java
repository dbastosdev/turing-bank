package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    @Test
    void existsByCustomerShouldReturnTrueIfCustomerExists() {
        // Arrange
        Account account = Factory.creaAccount();
        Customer customer = Factory.createCustomer();
        customer.setName("Elaine");
        account.setInitialDeposit(100.0);
        account.setCustomer(customer);
        // Act
        underTest.save(account);
        boolean result = underTest.existsByCustomer(customer);
        // Assert
        Assertions.assertEquals(true,result);
    }
}