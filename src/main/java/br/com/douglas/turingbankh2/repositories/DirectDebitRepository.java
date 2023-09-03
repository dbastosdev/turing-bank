package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.DirectDebit;
import br.com.douglas.turingbankh2.domain.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectDebitRepository extends JpaRepository<DirectDebit,Long> {
    List<DirectDebit> findByAccountId(Long id);
}
