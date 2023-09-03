package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.jsf.FacesContextUtils;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void existsByCpfShouldReturnTrueIfCpfExists() {
        // Arrange
        Customer customer = Factory.createCustomer();
        customer.setName("Carlos");
        customer.setCpf("115.801.213-99");
        // Act
        underTest.save(customer);
        boolean result = underTest.existsByCpf("115.801.213-99");
        // Assert
        Assertions.assertEquals(true,result);
    }
}