package br.com.douglas.turingbankh2.requests;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.DirectDebit;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.DirectDebitStatus;
import br.com.douglas.turingbankh2.domain.enums.DirectDebitType;
import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DirectDebitReq {

    String descriptionOfDebit;
    DirectDebitType directDebitType;
    Double valueOfDebit;
    LocalDate dateOfAuthorizationOfDebit;
    LocalDate initialDueDate;
    LocalDate actualDueDate;
    DirectDebitStatus directDualDateStatus;
    LocalDate paymentDate;
    Account account;

    public DirectDebitReq(DirectDebit directDebit){
        this.directDebitType = directDebit.getDirectDebitType();
        this.descriptionOfDebit = directDebit.getDescriptionOfDebit();
        this.valueOfDebit = directDebit.getValueOfDebit();
        this.dateOfAuthorizationOfDebit = directDebit.getDateOfAuthorizationOfDebit();
        this.initialDueDate = directDebit.getInitialDueDate();
        this.actualDueDate = directDebit.getActualDueDate();
        this.directDualDateStatus = directDebit.getDirectDualDateStatus();
        this.paymentDate = directDebit.getPaymentDate();
        this.account = directDebit.getAccount();
    }

}
