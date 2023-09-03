package br.com.douglas.turingbankh2.domain;

import br.com.douglas.turingbankh2.domain.enums.ContractType;
import br.com.douglas.turingbankh2.domain.enums.LoanContractStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_loan_contract")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode
public class LoanContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean directDebit;
    private LocalDateTime createdAt;
    private Double requestedAmount;
    private Double monthlyFee;
    private Double AnnualFee;
    private Double totalContractAmount;
    private Integer numberOfInstallments;
    private LoanContractStatus contractStatus;
    private ContractType contractType;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    private Boolean loanContractActive;
    private Boolean loanContractDirectDebit;

    public LoanContract(){
    }

    public LoanContract(Long id, Boolean directDebit, LocalDateTime createdAt, Double requestedAmount, Double monthlyFee, Double annualFee, Double totalContractAmount, Integer numberOfInstallments, LoanContractStatus contractStatus, ContractType contractType, Account account, Boolean loanContractActive) {
        this.id = id;
        this.directDebit = directDebit;
        this.createdAt = createdAt;
        this.requestedAmount = requestedAmount;
        this.monthlyFee = monthlyFee;
        this.AnnualFee = annualFee;
        this.totalContractAmount = totalContractAmount;
        this.numberOfInstallments = numberOfInstallments;
        this.contractStatus = contractStatus;
        this.contractType = contractType;
        this.account = account;
        this.loanContractActive = loanContractActive;
    }

    public LoanContract(Long id, Boolean directDebit, LocalDateTime createdAt, Double requestedAmount, Double monthlyFee, Double annualFee, Double totalContractAmount, Integer numberOfInstallments, LoanContractStatus contractStatus, ContractType contractType, Account account, Boolean loanContractActive, Boolean loanContractDirectDebit) {
        this.id = id;
        this.directDebit = directDebit;
        this.createdAt = createdAt;
        this.requestedAmount = requestedAmount;
        this.monthlyFee = monthlyFee;
        AnnualFee = annualFee;
        this.totalContractAmount = totalContractAmount;
        this.numberOfInstallments = numberOfInstallments;
        this.contractStatus = contractStatus;
        this.contractType = contractType;
        this.account = account;
        this.loanContractActive = loanContractActive;
        this.loanContractDirectDebit = loanContractDirectDebit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDirectDebit() {
        return directDebit;
    }

    public void setDirectDebit(Boolean directDebit) {
        this.directDebit = directDebit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(Double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public Double getAnnualFee() {
        return AnnualFee;
    }

    public void setAnnualFee(Double annualFee) {
        AnnualFee = annualFee;
    }

    public Double getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(Double totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public LoanContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(LoanContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean getLoanContractActive() {
        return loanContractActive;
    }

    public void setLoanContractActive(Boolean loanContractActive) {
        this.loanContractActive = loanContractActive;
    }

    public Boolean getLoanContractDirectDebit() {
        return loanContractDirectDebit;
    }

    public void setLoanContractDirectDebit(Boolean loanContractDirectDebit) {
        this.loanContractDirectDebit = loanContractDirectDebit;
    }
}
