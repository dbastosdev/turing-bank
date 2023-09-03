package br.com.douglas.turingbankh2.tests;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;

import java.time.LocalDateTime;

public class Factory {

    public static Customer createCustomer(){
        return new Customer(1L,
                            LocalDateTime.of(2002,01,04,13,50),
                            LocalDateTime.of(2002,01,05,13,50),
                "Ana", "ana@turingbank.com","123456","114.234.234-99");
    }

    public static CustomerRes createCustomerRes(){
        return new CustomerRes(1L,
                LocalDateTime.of(1956,01,04,13,50),
                LocalDateTime.of(1956,01,05,13,50),
                "Maria", "maria@turingbank.com","1354545","123.234.564-45");
    }

    public static CustomerReq createCustomerReq(){
        return new CustomerReq("Diego","diego@turingbank.com","23456","123.123.213.77");
    }

    public static Transaction createTransaction(){
        return new Transaction();
    }

    public static TransactionRes createTransactionRes(Transaction t){
        return new TransactionRes(t);
    }

    public static TransactionReq createTransactionReq(Transaction t){
        return new TransactionReq(t);
    }

    public static Account creaAccount() {
        return new Account();
    }

}
