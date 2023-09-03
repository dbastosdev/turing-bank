package br.com.douglas.turingbankh2.requests;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerReq {
    private String name;
    private String login;
    private String password;
    private String cpf;

    private String zipcode;
    private Integer number;

    public CustomerReq(){
    }

    public CustomerReq(String name, String login, String password, String cpf) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.cpf = cpf;
    }

    public CustomerReq(Customer entity){
        this.name = entity.getName();
        this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.cpf = entity.getCpf();
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
