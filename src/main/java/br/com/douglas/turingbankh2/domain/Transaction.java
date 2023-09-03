package br.com.douglas.turingbankh2.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private Double valueOfTransaction;
    private Double balanceBeforeTransaction;
    private Double balanceAfterTransaction;

    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable=false)
    private Account account;

    public Transaction(){
    }

    public Transaction(Long id, LocalDateTime createdAt, String description, Double valueOfTransaction, Double balanceBeforeTransaction, Double balanceAfterTransaction, Account account) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.valueOfTransaction = valueOfTransaction;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.account = account;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
