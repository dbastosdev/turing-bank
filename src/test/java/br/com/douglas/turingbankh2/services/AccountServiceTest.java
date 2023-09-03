package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.requests.AccountReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService service;

    @Mock
    private AccountRepository repository;

    @Test
    public void shouldReturnErrorWhenExistsByCustomer() {
        // Arrange/Mock
        Customer customer = new Customer();
        AccountReq accountReq = new AccountReq(1200.0, customer);

        Mockito.when(repository.existsByCustomer(customer)).thenReturn(true);

        // Action
        EntityDuplicatedException exception = Assertions.assertThrows(EntityDuplicatedException.class,
                () -> service.insert(accountReq),
                "Já há conta vinculada a este Cliente");

        // Assert/Verify
        Assertions.assertEquals("Já há conta vinculada a este Cliente", exception.getMessage());
    }

    @Test
    public void shouldReturnAccountResWhenExistsByCustomer() {
        // Arrange/Mock
        Customer customer = new Customer();
        AccountReq accountReq = new AccountReq(1200.0, customer);
        Account account = new Account(1L, LocalDateTime.now(), LocalDateTime.now(), "Teste", 1200.00, customer);
        AccountRes accountRes = new AccountRes(account);

        Mockito.when(repository.existsByCustomer(customer)).thenReturn(false);
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        // Action
        AccountRes retorno = service.insert(accountReq);

        // Assert/Verify
        Assertions.assertEquals(accountRes, retorno);
//        Assertions.assertEquals(account.getBalance(), retorno.getBalance());
//        Assertions.assertEquals(account.getAccountNumber(), retorno.getAccountNumber());
//        Assertions.assertEquals(customer, retorno.getCustomer());
//        Assertions.assertEquals(account.getCreatedAt(), retorno.getCreatedAt());
//        Assertions.assertEquals(account.getId(), retorno.getId());
//        Assertions.assertEquals(account.getUpdatedAt(), retorno.getUpdatedAt());

        Mockito.verify(repository).save(Mockito.any(Account.class));
    }
}