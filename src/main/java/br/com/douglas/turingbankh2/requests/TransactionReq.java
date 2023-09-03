package br.com.douglas.turingbankh2.requests;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Transaction;

import java.time.LocalDateTime;

public class TransactionReq {
    private String description;
    private Double valueOfTransaction;
    private Account account;

    public TransactionReq(){
    }

    public TransactionReq(String description, Double valueOfTransaction, Account account) {
        this.description = description;
        this.valueOfTransaction = valueOfTransaction;
        this.account = account;
    }

    public TransactionReq(Transaction transaction){
        this.description = transaction.getDescription();
        this.valueOfTransaction = transaction.getValueOfTransaction();
        this.account = transaction.getAccount();
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
}
