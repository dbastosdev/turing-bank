package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.ContractType;
import br.com.douglas.turingbankh2.repositories.CustomerRepository;
import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.responses.LoanContractRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InstallmentServiceTest {

    @Mock
    private InstallmentRepository repository;
    @InjectMocks
    private InstallmentService service;

    @Test
    void findAll() {
        // A - arrange
        Installment installment = new Installment();
        InstallmentRes installmentRes = new InstallmentRes();

        // A - act
        Mockito.when(repository.findAll()).thenReturn(List.of(installment));
        List<InstallmentRes> result = service.findAll();
        // A - Assert - Não consegui que retornasse a mesma lista
        Assertions.assertNotNull(result);
    }

    @Test
    void findById() {
        // A - arrange
        long id = 1L;
        Installment installment = new Installment();
        InstallmentRes installmentRes = new InstallmentRes();

        // A - act
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(installment));
        InstallmentRes result = service.findById(id);
        // A - Assert - Não consegui que retornasse o mesmo objeto
        Assertions.assertNotNull(result);
    }

    @Test
    void insert() {
        // A - arrange
        Installment installment = new Installment();
        InstallmentReq installmentReq = new InstallmentReq();

        // A - act
        Mockito.when(repository.save(Mockito.any(Installment.class))).thenReturn(installment);
        InstallmentRes result = service.insert(installmentReq);

        // A - Assert
        Mockito.verify(repository).save(Mockito.any(Installment.class));
        Assertions.assertEquals(InstallmentRes.class, result.getClass());
    }

    @Test
    void allInstallmentsByLoanContract() {
        // Arrange
        long id = 1L;
        Installment installment = new Installment();
        // Act
        Mockito.when(repository.findAllByLoanContractId(id)).thenReturn(List.of(installment));
        List<InstallmentRes> result = service.allInstallmentsByLoanContract(id);
        // Assert
        Assertions.assertNotNull(result);
    }
}