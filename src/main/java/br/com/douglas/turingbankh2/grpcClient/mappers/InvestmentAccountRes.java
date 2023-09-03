package br.com.douglas.turingbankh2.grpcClient.mappers;

import br.com.douglas.turingbankh2.grpcClient.enums.InvestmentAccountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvestmentAccountRes {
    private Long id;
    private String description;
    private String investmentAccountNumber;
    private InvestmentAccountType investmentAccountType;
    private Long accountId;
    private Double totalValue;
    private Double balance;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private LocalDateTime birthdayDate;
    private Double fee;
    private Long customerId;

    public InvestmentAccountRes(InvestmentAccount investmentAccount){
        this.investmentAccountNumber = investmentAccount.getInvestmentAccountNumber();
        this.id = investmentAccount.getId();
        this.description = investmentAccount.getDescription();
        this.investmentAccountType = investmentAccount.getInvestmentAccountType();
        this.accountId = investmentAccount.getAccountId();
        this.totalValue = investmentAccount.getTotalValue();
        this.balance = investmentAccount.getBalance();
        this.createdAt = investmentAccount.getCreatedAt();
        this.updatedAt = investmentAccount.getUpdatedAt();
        this.birthdayDate = investmentAccount.getBirthdayDate();
        this.fee = investmentAccount.getFee();
        this.customerId = investmentAccount.getCustomerId();
    }

    @Override
    public String toString() {
        return "InvestmentAccountRes{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", investmentAccountNumber='" + investmentAccountNumber + '\'' +
                ", investmentAccountType=" + investmentAccountType +
                ", accountId=" + accountId +
                ", totalValue=" + totalValue +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", birthdayDate=" + birthdayDate +
                ", fee=" + fee +
                ", customerId=" + customerId +
                '}';
    }
}
