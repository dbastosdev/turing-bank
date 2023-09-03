package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.services.AddressService;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    //@Order(1)
    void findAll() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/addresses";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    //@Order(2)
    void findById() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/addresses/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(404, status);
    }

    @Test
    //@Order(3)
    void insert() throws Exception {
        // Assert
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/addresses";
        Address address = new Address();
        Customer customer = new Customer();
        customer.setId(6L);
        address.setZipcode("20.940-070");
        address.setNumber(1);
        address.setCustomerForAddress(customer);

        // Act
        String inputJson = objectMapper.writeValueAsString(address);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        //assertNotEquals(201,status);
    }

    @Test
    //@Order(4)
    void update() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/addresses/2";
        Address address = new Address();
        address.setZipcode("20.940-070");
        String inputJson = objectMapper.writeValueAsString(address);
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
   // @Order(5)
    void delete() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/addresses/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(204, status);
    }
}