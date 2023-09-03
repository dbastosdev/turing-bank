package br.com.douglas.turingbankh2.grpcClient.mappers;

import br.com.douglas.turingbankh2.grpcClient.enums.InvestmentAccountType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvestmentAccountReq {
    private String description;
    private InvestmentAccountType investmentAccountType;
    private String investmentAccountNumber;
    private Long accountId;
    private Double totalValue;
    private Double fee;
    private Long customerId;

    public InvestmentAccountReq(InvestmentAccount investmentAccount){
        this.investmentAccountNumber = investmentAccount.getInvestmentAccountNumber();
        this.description = investmentAccount.getDescription();
        this.investmentAccountType = investmentAccount.getInvestmentAccountType();
        this.accountId = investmentAccount.getAccountId();
        this.totalValue = investmentAccount.getTotalValue();
        this.fee = investmentAccount.getFee();
        this.customerId = investmentAccount.getCustomerId();
    }

    @Override
    public String toString() {
        return "InvestmentAccountReq{" +
                "description='" + description + '\'' +
                ", investmentAccountType=" + investmentAccountType +
                ", investmentAccountNumber='" + investmentAccountNumber + '\'' +
                ", accountId=" + accountId +
                ", totalValue=" + totalValue +
                ", fee=" + fee +
                ", customerId=" + customerId +
                '}';
    }
}
