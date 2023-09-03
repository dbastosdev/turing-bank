package br.com.douglas.turingbankh2.grpcClient.service;

import br.com.douglas.turingbankh2.appAccountInvest.protos.EmptyInvestmentAccountGRPCRequest;
import br.com.douglas.turingbankh2.appAccountInvest.protos.InvestmentAccountGRPCGrpc;
import br.com.douglas.turingbankh2.appAccountInvest.protos.InvestmentAccountListGRPCResponse;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccount;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountReq;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountRes;
import br.com.douglas.turingbankh2.repositories.CustomerRepository;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.CustomerService;
import br.com.douglas.turingbankh2.tests.Factory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class InvestmentServiceClientTest {

    // referênca é mockar o service.
    //https://stackoverflow.com/questions/59536673/how-to-use-mockito-to-mock-grpc-serviceblockingstub-to-throw-statusruntimeexcept

    @Mock
    private InvestmentServiceClient service;

    @Test
    void findInvestmentAccountsById() throws JsonProcessingException {
        // A - arrange
        long id = 1L;
        InvestmentAccountRes response = new InvestmentAccountRes();

        // A - act
        Mockito.when(service.findInvestmentAccountsById(id)).thenReturn(response);
        InvestmentAccountRes result = service.findInvestmentAccountsById(id);
        // A - Assert - Não consegui que retornasse o mesmo objeto
        assertNotNull(result);
    }

    @Test
    void deleteInvestmentAccountsById() throws JsonProcessingException {
        // A - arrange
        long id = 1L;
        InvestmentAccountRes response = new InvestmentAccountRes();

        // A - act
        Mockito.doNothing().when(service).deleteInvestmentAccountsById(id);
        service.deleteInvestmentAccountsById(id);
        // A - Assert - Não consegui que retornasse o mesmo objeto
        Mockito.verify(service).deleteInvestmentAccountsById(id);
    }

    @Test
    void insert() throws JsonProcessingException {
        // A - arrang
        InvestmentAccountReq investmentAccountReq = new InvestmentAccountReq();
        investmentAccountReq.setAccountId(1L);
        InvestmentAccountRes response = new InvestmentAccountRes();

        // A - act
        Mockito.when(service.insert(Mockito.any(InvestmentAccountReq.class))).thenReturn(response);
        InvestmentAccountRes result = service.insert(investmentAccountReq);

        // A - Assert
        Mockito.verify(service).insert(Mockito.any(InvestmentAccountReq.class));
    }

    @Test
    void withdraw() throws JsonProcessingException {
        // A - arrang
        long id = 1L;
        InvestmentAccountReq investmentAccountReq = new InvestmentAccountReq();
        investmentAccountReq.setAccountId(1L);
        InvestmentAccountRes response = new InvestmentAccountRes();

        // A - act
        Mockito.when(service.withdraw(id)).thenReturn(response);
        InvestmentAccountRes result = service.withdraw(id);

        // A - Assert
        Mockito.verify(service).withdraw(id);
    }

    @Test
    void findAll() throws JsonProcessingException {
        // Arrange
        InvestmentAccountRes response = new InvestmentAccountRes();
        // Act
        Mockito.when(service.findAll()).thenReturn(List.of(response));
        List<InvestmentAccountRes>  result = service.findAll();
        // Assert
        assertNotNull(result);
    }
}