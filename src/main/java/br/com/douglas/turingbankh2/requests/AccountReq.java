package br.com.douglas.turingbankh2.requests;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
public class AccountReq {

    private Double balance;
    private Customer customer;

    private Double initialDeposit;

    private Double creditLimit;

    public AccountReq(){
    }

    public AccountReq(Double initialDeposit, Customer customer) {
        this.balance = initialDeposit;
        this.customer = customer;
    }

    public AccountReq(Account account){
        this.balance = account.getInitialDeposit();
        this.customer = account.getCustomer();
        this.initialDeposit = account.getInitialDeposit();
        this.creditLimit = account.getCreditLimit();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }
}
