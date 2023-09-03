package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository underTest;

    @Test
    void existsByCustomerForAddress() {
        // Arrange
        Address address = new Address();
        Customer customer = Factory.createCustomer();
        customer.setName("Elaine");
        address.setCustomerForAddress(customer);
        // Act
        underTest.save(address);
        boolean result = underTest.existsByCustomerForAddress(customer);
        // Assert
        Assertions.assertEquals(true,result);
    }
}