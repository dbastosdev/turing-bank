package br.com.douglas.turingbankh2.controllers;

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
class InstallmentControllerTest {

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
        String uri = "/installments";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    void findById() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/installments/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(404, status);
    }

    @Test
    void findAllByAccount() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/installments/by-contract/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }
}