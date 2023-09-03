package br.com.douglas.turingbankh2.grpcClient.mappers;


import br.com.douglas.turingbankh2.grpcClient.enums.InvestmentAccountType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_investment_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InvestmentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String investmentAccountNumber;
    private String description;
    private InvestmentAccountType investmentAccountType;
    private Long accountId;
    private Double totalValue;
    private Double balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime birthdayDate;
    private Double fee;
    private Long customerId;

}
