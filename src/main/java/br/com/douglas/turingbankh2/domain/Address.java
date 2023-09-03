package br.com.douglas.turingbankh2.domain;

import br.com.douglas.turingbankh2.responses.AddressRes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipcode;
    private String fu;
    private String city;
    private Integer number;
    private String district;
    private String street;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonBackReference
    private Customer customerForAddress;

    public Address(AddressRes a) {
        this.id = a.getId();
        this.zipcode = a.getZipcode();
        this.fu = a.getFu();
        this.city = a.getCity();
        this.number = a.getNumber();
        this.district = a.getDistrict();
        this.street = a.getStreet();
        this.createdAt = a.getCreatedAt();
        this.updatedAt = a.getUpdatedAt();
    }
}
