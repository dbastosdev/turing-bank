package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.domain.enums.ContractType;
import br.com.douglas.turingbankh2.domain.enums.LoanContractStatus;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
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
class LoanContractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper; // Faz a conversão de Objeto para String para tráfego pela rede.

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    void findAll() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/loan-contracts";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }



    @Test
    void findLoanContractByAccount() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/loan-contracts/by-account/";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(404, status);
    }

    @Test
    void insert() throws Exception {
        // Assert
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/loan-contracts";
        LoanContract contract = new LoanContract();
        contract.setRequestedAmount(1000.0);
        contract.setNumberOfInstallments(2);
        contract.setContractType(ContractType.PESSOAL);
        Account acc = Factory.creaAccount();
        acc.setId(1L);
        contract.setAccount(acc);

        // Act
        String inputJson = objectMapper.writeValueAsString(contract);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        //assertEquals(201, status);
        mvcResult.getResponse().equals(LoanContract.class);
    }
}

