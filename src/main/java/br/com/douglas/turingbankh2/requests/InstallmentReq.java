package br.com.douglas.turingbankh2.requests;

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
public class InstallmentReq {
    private LocalDateTime limitDate;
    private Double totalValue;
    private LocalDateTime paymentDate;
    private InstallmentStatus installmentStatus;

    private LoanContract loanContract;

    public InstallmentReq(Installment installment){
        this.limitDate = installment.getLimitDate();
        this.totalValue = installment.getTotalValue();
        this.paymentDate = installment.getPaymentDate();
        this.installmentStatus = installment.getInstallmentStatus();
        this.loanContract = installment.getLoanContract();
    }

}
