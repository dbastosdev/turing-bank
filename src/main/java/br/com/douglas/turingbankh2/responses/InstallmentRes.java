package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InstallmentRes {
    private Long id;
    private LocalDateTime limitDate;
    private Double totalValue;
    private LocalDateTime paymentDate;
    private InstallmentStatus installmentStatus;

    private LoanContract loanContract;

    public InstallmentRes(Installment installment){
        this.id = installment.getId();
        this.limitDate = installment.getLimitDate();
        this.totalValue = installment.getTotalValue();
        this.paymentDate = installment.getPaymentDate();
        this.installmentStatus = installment.getInstallmentStatus();
        this.loanContract = installment.getLoanContract();
    }

}
