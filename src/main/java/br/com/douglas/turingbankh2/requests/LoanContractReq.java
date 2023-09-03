package br.com.douglas.turingbankh2.requests;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.ContractType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoanContractReq {

    private Double requestedAmount;
    private Boolean directDebit;
    private Integer numberOfInstallments;
    private ContractType contractType;
    private Account account;

    private Boolean loanContractActive;
    private Boolean loanContractDirectDebit;

    public LoanContractReq(LoanContract loanContract){
        this.directDebit = loanContract.getDirectDebit();
        this.requestedAmount = loanContract.getRequestedAmount();
        this.numberOfInstallments = loanContract.getNumberOfInstallments();
        this.contractType = loanContract.getContractType();
        this.account = loanContract.getAccount();
        this.loanContractActive = loanContract.getLoanContractActive();
        this.loanContractDirectDebit = loanContract.getLoanContractDirectDebit();
    }

}
