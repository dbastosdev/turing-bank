package br.com.douglas.turingbankh2.requests;

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
public class AddressReq {

    private String zipcode;
    private String fu;
    private String city;
    private Integer number;
    private String district;
    private String street;
    private Customer customerForAddress;

    public AddressReq(Address address){
        this.zipcode = address.getZipcode();
        this.fu = address.getFu();
        this.city = address.getCity();
        this.number = address.getNumber();
        this.district = address.getDistrict();
        this.street = address.getStreet();
        this.customerForAddress = address.getCustomerForAddress();
    }

}
