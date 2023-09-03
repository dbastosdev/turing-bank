package br.com.douglas.turingbankh2.responses;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;

import java.time.LocalDateTime;

public class CustomerRes {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String login;
    private String password;

    private String cpf;

    private Address address;

    public CustomerRes(){
    }

    public CustomerRes(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String login, String password, String cpf) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
    }

    public CustomerRes(Customer entity){
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.name = entity.getName();
        this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.cpf = entity.getCpf();
        this.address = entity.getAddress();
    }

    public CustomerRes(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String login, String password, String cpf, Address address) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerRes{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", cpf='" + cpf + '\'' +
                ", address=" + address +
                '}';
    }
}
