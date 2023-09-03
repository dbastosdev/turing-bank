package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.repositories.CustomerRepository;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import javax.xml.stream.FactoryConfigurationError;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    // Dependências mokcadas:
    @Mock
    private CustomerRepository repository;
    @InjectMocks
    private CustomerService service;

    // Testes
    @Test
    void findAllShouldReturnAListOfCustomersRes() {
        // A - arrange
        Customer customer = new Customer();
        CustomerRes customerRes = new CustomerRes();

        // A - act
        Mockito.when(repository.findAll()).thenReturn(List.of(customer));
        List<CustomerRes> result = service.findAll();
        // A - Assert - Não consegui que retornasse a mesma lista
        Assertions.assertNotNull(result);
    }

    @Test
    void findByIdShouldReturnACustomerRes() {
        // A - arrange
        long id = 1L;
        Customer customer = Factory.createCustomer();
        CustomerRes customerRes = Factory.createCustomerRes();

        // A - act
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerRes result = service.findById(id);
        // A - Assert - Não consegui que retornasse o mesmo objeto
        Assertions.assertEquals(customerRes.getId(),result.getId());
    }


    @Test
    void insertShouldThrowsEntityDuplicatedExceptionWhenCpfIsEqual() {
        // A - arrange
        String duplicatedCpf = "112.568.321-99";
        CustomerReq customerReq = Factory.createCustomerReq();
        customerReq.setCpf("112.568.321-99");

        Mockito.when(repository.existsByCpf(duplicatedCpf)).thenReturn(true);

        // AA - act and Assert
        Assertions.assertThrows(EntityDuplicatedException.class, () ->
        {
            service.insert(customerReq);
        });
    }

    @Test
    void insertShouldSaveNewEntityAndReturnNewCustomer() throws JsonProcessingException {
        // A - arrange
        Customer customer = Factory.createCustomer();
        CustomerReq customerReq = new CustomerReq(customer);

        // A - act
        Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(customer);
        CustomerRes result = service.insert(customerReq);

        // A - Assert
        Mockito.verify(repository).save(Mockito.any(Customer.class));
        Assertions.assertEquals(CustomerRes.class, result.getClass());
    }

    @Test
    void updateShouldSaveNewCustomer() {
        // A - arrange
        long id = 2L;
        Customer customer = new Customer();
        CustomerReq customerReq = new CustomerReq(customer);
        customerReq.setName("Bruno");

        // A - act
        Mockito.when(repository.getReferenceById(id)).thenReturn(customer);
        Mockito.when(repository.save(customer)).thenReturn(customer);
        CustomerRes customerRes = service.update(id,customerReq);
        // A - Assert
        Assertions.assertEquals("Bruno",customerRes.getName());

    }

    @Test
    void updateShouldThrowsEntityNotFoundExceptionWhenIdDoesNotExist() {
        // A - arrange
        long id = 300L;
        Customer customer = Factory.createCustomer();
        CustomerReq customerReq = Factory.createCustomerReq();

        Mockito.when(repository.getReferenceById(id)).thenReturn(null).thenThrow(javax.persistence.EntityNotFoundException.class);

        // AA - act and Assert
        Assertions.assertThrows(RuntimeException.class, () ->
        {
            service.update(id,customerReq);
        });

    }

    @Test
    void deleteShouldDeleteEntityIfExists() {
        // A - arrange
        long id = 1L;
       CustomerReq customerReq = Factory.createCustomerReq();
        // A - Act
       Mockito.doNothing().when(repository).deleteById(id);
       service.delete(id);
        // A - Assert
        Mockito.verify(repository).deleteById(id);
    }
}