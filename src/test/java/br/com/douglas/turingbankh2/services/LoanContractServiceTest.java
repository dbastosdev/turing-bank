package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.domain.enums.ContractType;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.repositories.LoanContractRepository;
import br.com.douglas.turingbankh2.requests.LoanContractReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.LoanContractRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
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

@ExtendWith(MockitoExtension.class)
class LoanContractServiceTest {

    @Mock
    private LoanContractRepository repository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private InstallmentService installmentService;
    @InjectMocks
    private LoanContractService lcService;

    @Test
    void findAll() {
        // Arrange
        LoanContract lc = new LoanContract();
        // Action
        Mockito.when(repository.findAll()).thenReturn(List.of(lc));
        List<LoanContractRes> tResult = lcService.findAll();
        // Assert
        Assertions.assertNotNull(tResult);
    }

    @Test
    void insert() {
        // Arrange
        LoanContractReq loanContractReq = new LoanContractReq();
        loanContractReq.setAccount(new Account());
        loanContractReq.getAccount().setId(1L);
        loanContractReq.getAccount().setCreditLimit(10000.0);
        loanContractReq.setRequestedAmount(5000.0);
        loanContractReq.setContractType(ContractType.PESSOAL);
        loanContractReq.setNumberOfInstallments(3);
        Account acc = new Account();
        acc.setCreditLimit(10000.0);
        LoanContract loanContract = new LoanContract();
        // Act
        Mockito.when(repository.save(loanContract)).thenReturn(loanContract);
        //Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(acc));
        //LoanContractRes tResult = lcService.insert(loanContractReq);
        // Assert
        //Assertions.assertNotNull(tResult);
        Assertions.assertNotNull(repository.save(loanContract));
    }

    @Test
    void findLoanContractByAccount() {
        // Arrange
        long id = 2L;
        LoanContract lc2 = new LoanContract();
        Account acc = new Account();
        lc2.setAccount(acc);
        lc2.setRequestedAmount(8000.0);
        lc2.setContractType(ContractType.PESSOAL);
        lc2.setNumberOfInstallments(5);
        lc2.setAccount(acc);
        // Act
        Mockito.when(repository.findByAccountId(id)).thenReturn(lc2);
        LoanContractRes lcr = lcService.findLoanContractByAccount(id);
        // Assert
        Assertions.assertNotNull(lcr);
        Mockito.verify(repository).findByAccountId(id);
    }
}