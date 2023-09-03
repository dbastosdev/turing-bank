package br.com.douglas.turingbankh2.repositories;

import br.com.douglas.turingbankh2.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByCpf(String cpf);
}
