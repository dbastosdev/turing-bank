package br.com.douglas.turingbankh2.domain;

import br.com.douglas.turingbankh2.requests.AccountReq;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String accountNumber;

    private Double initialDeposit;
    private Double balance;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonBackReference
    private Customer customer;

//    @OneToMany(mappedBy="account")
//    private Set<Transaction> setOfTransactions = new HashSet<>();

    private Double creditLimit;

    public Account(){
    }

    public Account(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String accountNumber, Double initialDeposit, Customer customer) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.customer = customer;
    }

    public Account(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String accountNumber, Double initialDeposit, Double balance, Customer customer, Double creditLimit) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accountNumber = accountNumber;
        this.initialDeposit = initialDeposit;
        this.customer = customer;
        this.creditLimit = creditLimit;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    //    public Set<Transaction> getSetOfTransactions() {
//        return setOfTransactions;
//    }
}
