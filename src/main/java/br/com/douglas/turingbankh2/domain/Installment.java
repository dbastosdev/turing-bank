package br.com.douglas.turingbankh2.domain;


import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import br.com.douglas.turingbankh2.domain.enums.LoanContractStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_installment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime limitDate;
    private Double totalValue;
    private LocalDateTime paymentDate;
    private InstallmentStatus installmentStatus;

    @ManyToOne
    @JoinColumn(name = "loan_contract_id", referencedColumnName = "id")
    private LoanContract loanContract;

}
