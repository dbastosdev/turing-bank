package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class InstallmentRepositoryTest {

    @Autowired
    private InstallmentRepository itUnderTest;
    @Autowired
    private AccountRepository accUnderTest;
    @Autowired
    private LoanContractRepository lcUnderTest;

    @Test
    void findAllByLoanContractId() {
        // Arrange
        LoanContract lc = new LoanContract();
        Account acc = new Account();
        acc.setAccountNumber("1234");
        accUnderTest.save(acc);
        lc.setAccount(acc);
        lcUnderTest.save(lc);
        Installment it = new Installment();
        it.setLoanContract(lc);
        itUnderTest.save(it);

        // Act
        List<Installment> result = itUnderTest.findAllByLoanContractId(1L);
        // Assert
        Assertions.assertNotNull(result);
    }
}