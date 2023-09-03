package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.ContractType;
import br.com.douglas.turingbankh2.domain.enums.LoanContractStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoanContractRes {

    private Long id;
    private LocalDateTime createdAt;
    private Double requestedAmount;
    private Boolean directDebit;
    private Double monthlyFee;
    private Double AnnualFee;
    private Double totalContractAmount;
    private Integer numberOfInstallments;
    private LoanContractStatus contractStatus;
    private ContractType contractType;

    private Account account;

    private Boolean loanContractActive;
    private Boolean loanContractDirectDebit;

    public LoanContractRes(LoanContract loanContract) {
        this.directDebit = loanContract.getDirectDebit();
        this.id = loanContract.getId();
        this.createdAt = loanContract.getCreatedAt();
        this.requestedAmount = loanContract.getRequestedAmount();
        this.monthlyFee = loanContract.getMonthlyFee();
        this.AnnualFee = loanContract.getAnnualFee();
        this.totalContractAmount = loanContract.getTotalContractAmount();
        this.numberOfInstallments = loanContract.getNumberOfInstallments();
        this.contractStatus = loanContract.getContractStatus();
        this.contractType = loanContract.getContractType();
        this.account = loanContract.getAccount();
        this.loanContractActive = loanContract.getLoanContractActive();
        this.loanContractDirectDebit = loanContract.getLoanContractDirectDebit();
    }
}
