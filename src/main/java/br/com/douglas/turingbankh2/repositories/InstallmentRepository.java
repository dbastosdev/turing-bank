package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment,Long> {
    List<Installment> findAllByLoanContractId(Long id);
}
