package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Transaction;

import java.time.LocalDateTime;

public class TransactionRes {
    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private Double valueOfTransaction;

    private Double balanceBeforeTransaction;
    private Double balanceAfterTransaction;

    private Account account;



    public TransactionRes(){
    }

    public TransactionRes(Long id, LocalDateTime createdAt, String description, Double valueOfTransaction, Double balanceBeforeTransaction, Double balanceAfterTransaction, Account account) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.valueOfTransaction = valueOfTransaction;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.account = account;
    }

    public TransactionRes(Transaction transaction){
        this.id = transaction.getId();
        this.createdAt = transaction.getCreatedAt();
        this.description = transaction.getDescription();
        this.valueOfTransaction = transaction.getValueOfTransaction();
        this.account = transaction.getAccount();
        this.balanceBeforeTransaction = transaction.getBalanceBeforeTransaction();
        this.balanceAfterTransaction = transaction.getBalanceAfterTransaction();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValueOfTransaction() {
        return valueOfTransaction;
    }

    public void setValueOfTransaction(Double valueOfTransaction) {
        this.valueOfTransaction = valueOfTransaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getBalanceBeforeTransaction() {
        return balanceBeforeTransaction;
    }

    public void setBalanceBeforeTransaction(Double balanceBeforeTransaction) {
        this.balanceBeforeTransaction = balanceBeforeTransaction;
    }

    public Double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(Double balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
}
