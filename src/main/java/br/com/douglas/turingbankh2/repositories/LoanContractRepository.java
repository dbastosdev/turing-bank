package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanContractRepository extends JpaRepository<LoanContract,Long> {
    boolean existsByAccount(Account account);
    LoanContract findByAccountId(Long id);
}
