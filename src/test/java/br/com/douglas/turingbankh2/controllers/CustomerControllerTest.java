package br.com.douglas.turingbankh2.controllers;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.stream.FactoryConfigurationError;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Ref.:
// https://medium.com/@gcbrandao/testando-uma-api-rest-spring-boot-2-com-junit5-e-mockmvc-db603c65a306
// https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper; // Faz a conversão de Objeto para String para tráfego pela rede.

    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void testfindAllShouldReturn200StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    public void insertNewCustomerShouldReturn201StatusCode() throws Exception {
        // Assert
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers";
        Customer customer = Factory.createCustomer();
        customer.setName("Bruno");
        customer.setLogin("bruno123");
        customer.setPassword("fadf38935");
        customer.setCpf("324.535.097-00");

        // Act
        String inputJson = objectMapper.writeValueAsString(customer);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void findCustomerByIdShouldReturn200StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers/1";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    public void uPdateCustomerByIdShouldReturn200StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers/2";
        Customer customer = Factory.createCustomer();
        customer.setName("Bruno/luiz");
        String inputJson = objectMapper.writeValueAsString(customer);;
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(200, status);
    }

    @Test
    public void deleteByIdShouldReturn204StatusCode() throws Exception {
        // Arred
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        String uri = "/customers/2";
        // Act
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        // Assert
        assertEquals(204, status);
    }
}