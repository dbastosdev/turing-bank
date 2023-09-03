package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountRes {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String accountNumber;
    private Double balance;
    private Double initialDeposit;
    private Customer customer;

    private Double creditLimit;

    public AccountRes(Account account){
        this.id = account.getId();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.accountNumber = account.getAccountNumber();
        this.initialDeposit = account.getInitialDeposit();
        this.balance = account.getBalance();
        this.customer = account.getCustomer();
        this.creditLimit = account.getCreditLimit();
    }
}
