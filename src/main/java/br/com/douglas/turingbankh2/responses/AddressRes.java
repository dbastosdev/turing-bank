package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddressRes {
    private Long id;
    private String zipcode;
    private String fu;
    private String city;
    private Integer number;
    private String district;
    private String street;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Setter
    private Customer customerForAddress;

    public AddressRes(Address address){
        this.id = address.getId();
        this.zipcode = address.getZipcode();
        this.fu = address.getFu();
        this.city = address.getCity();
        this.number = address.getNumber();
        this.district = address.getDistrict();
        this.street = address.getStreet();
        this.createdAt = address.getCreatedAt();
        this.updatedAt = address.getUpdatedAt();
        this.customerForAddress = address.getCustomerForAddress();
    }

    public Long getCustomerForAddress() {
        if(customerForAddress == null){
            return null;
        }
        return customerForAddress.getId();
    }
}
