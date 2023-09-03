package br.com.douglas.turingbankh2.grpcClient.controller;

import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccount;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountReq;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountRes;
import br.com.douglas.turingbankh2.grpcClient.service.InvestmentServiceClient;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class InvestimentServiceControllerTest {

    // Para funcionar precisa do outro serviço em operação.

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Mock
    private InvestmentServiceClient service;


    @Test
    void findInvestmentAccount() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/internal-client-investment/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    void deleteInvestmentAccountsById() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/internal-client-investment/2";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    void findAll() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/internal-client-investment";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }
}