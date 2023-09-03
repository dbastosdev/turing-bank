package br.com.douglas.turingbankh2.domain;


import br.com.douglas.turingbankh2.requests.CustomerReq;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String login;
    private String password;
    private String cpf;

    @Transient
    private String zipcode;
    @Transient
    private Integer number;

    @OneToOne(mappedBy = "customer")
    @JsonBackReference
    private Account account;

    @OneToOne(mappedBy = "customerForAddress")
    private Address address;

    public Customer(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String login, String password, String cpf) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
    }


}

//@Entity
//@Table(name = "tb_customer")
//public class Customer{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private String name;
//    private String login;
//    private String password;
//    private String cpf;
//
//    public Customer(){
//    }
//
//    public Customer(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String login, String password, String cpf) {
//        this.id = id;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//        this.name = name;
//        this.login = login;
//        this.password = password;
//        this.cpf = cpf;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public void setLogin(String login) {
//        this.login = login;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }
//}
