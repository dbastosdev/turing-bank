package br.com.douglas.turingbankh2.domain;

import br.com.douglas.turingbankh2.domain.enums.DirectDebitStatus;
import br.com.douglas.turingbankh2.domain.enums.DirectDebitType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_direct_debit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DirectDebit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    DirectDebitType directDebitType;
    String descriptionOfDebit;
    Double valueOfDebit;
    LocalDate dateOfAuthorizationOfDebit;
    LocalDate initialDueDate;
    LocalDate actualDueDate;
    DirectDebitStatus directDualDateStatus;
    LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable=false)
    Account account;


}
