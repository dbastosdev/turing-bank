package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper; // Faz a conversão de Objeto para String para tráfego pela rede.

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    void findAllShouldReturn200StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/transactions";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    void findTransactionsByIdhouldReturn404StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/transactions/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(404, status);
    }

    @Test
    void insertNewCustomerShouldReturn201StatusCode() throws Exception {
        // Assert
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers";
        Transaction transaction = Factory.createTransaction();
        transaction.setDescription("SAQUE");
        transaction.setValueOfTransaction(300.0);
        Account acc = Factory.creaAccount();
        acc.setId(1L);
        transaction.setAccount(acc);

        // Act
        String inputJson = objectMapper.writeValueAsString(transaction);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void findAllByAccountShouldReturn200StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/transactions/account/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }
}