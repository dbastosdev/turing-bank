package br.com.douglas.turingbankh2.grpcClient.service;

import br.com.douglas.turingbankh2.appAccountInvest.protos.*;
import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccount;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountReq;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountRes;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.TransactionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentServiceClient {

    @GrpcClient("turingbank")
    private InvestmentAccountGRPCGrpc.InvestmentAccountGRPCBlockingStub investmentAccountGRPCBlockingStub;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;


    public InvestmentAccountRes findInvestmentAccountsById(Long id) throws JsonProcessingException {
        // Passa o id para o método grpc do serviço externo e pega a resposta
        FindInvestmentAccountByIdGRPCResponse response = investmentAccountGRPCBlockingStub.findInvestmentAccountById(FindInvestmentAccountByIdGRPCRequest.newBuilder().setId(id).build());
        // Converte a resposta para objeto Java
        InvestmentAccountRes investmentAccountRes = new InvestmentAccountRes();
        investmentAccountRes = objectMapper.readValue(response.getInvestmentAccount(), InvestmentAccountRes.class);

        return investmentAccountRes;
    }

    public void deleteInvestmentAccountsById(Long id) throws JsonProcessingException {
        // Passa o id para o método grpc do serviço externo
        investmentAccountGRPCBlockingStub.deleteInvestmentAccount(InvestmentAccountIdGRPCRequest.newBuilder().setId(id).build());
    }

    public InvestmentAccountRes insert(InvestmentAccountReq investmentAccountReq) throws JsonProcessingException {

        // Criar o request grpc com base na requisição postman
        InvestmentAccountRequest investmentAccountRequestGRPC = InvestmentAccountRequest.newBuilder()
                .setFee(investmentAccountReq.getFee())
                .setDescription(investmentAccountReq.getDescription())
                .setInvestmentAccountType(InvestmentAccountRequest.InvestmentAccountType.valueOf(investmentAccountReq.getInvestmentAccountType().toString()))
                .setAccountId(investmentAccountReq.getAccountId())
                .setTotalValue(investmentAccountReq.getTotalValue())
                .setInvestmentAccountNumber(investmentAccountReq.getInvestmentAccountNumber())
                .setAccountId(investmentAccountReq.getAccountId())
                .setCustomerId(investmentAccountReq.getCustomerId())
                .build();

        // Faz a operação gRPC com a requisição grpc
        InvestmentAccountGRPCResponse responseGRPC = investmentAccountGRPCBlockingStub.insertInvestmentAccount(investmentAccountRequestGRPC);

        // Retorno de sucesso após converter para Objeto
        InvestmentAccountRes investmentAccountRes = new InvestmentAccountRes();
        investmentAccountRes = objectMapper.readValue(responseGRPC.getInvestmentAccount(), InvestmentAccountRes.class);

        // Se foi bem sucedido o cadastro de uma nova conta de investimento, realiza a transação
        if(investmentAccountRes.getId() != null){
            // Insere uma nova transação para atualizar a conta de onde partem os recursos para o investimento
            TransactionReq t = new TransactionReq();
            t.setDescription("SAQUE"); // Para indicar uma retirada da conta.
            t.setValueOfTransaction(investmentAccountReq.getTotalValue());
            Account acc = new Account();
            acc.setId(investmentAccountReq.getAccountId());
            t.setAccount(acc);
            transactionService.insert(t);
        }

        return investmentAccountRes;
    }

    public InvestmentAccountRes withdraw(Long id) throws JsonProcessingException {
        // Criar o request grpc com base na requisição postman
        InvestmentAccountIdGRPCRequest investmentAccountIdGRPCRequest = InvestmentAccountIdGRPCRequest.newBuilder()
                .setId(id)
                .build();

        // Guarda em um objeto dados do banco antes de efetuar o saque
        FindInvestmentAccountByIdGRPCResponse responseFindById = investmentAccountGRPCBlockingStub.findInvestmentAccountById(FindInvestmentAccountByIdGRPCRequest.newBuilder().setId(id).build());
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount = objectMapper.readValue(responseFindById.getInvestmentAccount(), InvestmentAccount.class);

        // Faz a operação gRPC com a requisição grpc
        InvestmentAccountGRPCResponse responseGRPC = investmentAccountGRPCBlockingStub.withdrawInvestmentAccount(investmentAccountIdGRPCRequest);

        // Retorno de sucesso após converter para Objeto
        InvestmentAccountRes investmentAccountRes = new InvestmentAccountRes();
        investmentAccountRes = objectMapper.readValue(responseGRPC.getInvestmentAccount(), InvestmentAccountRes.class);

        if(investmentAccountRes.getId() != null){
            // Insere uma nova transação para atualizar a conta de onde partem os recursos para o investimento

            TransactionReq t = new TransactionReq();
            t.setDescription("DEPOSITO"); // Para indicar uma retirada da conta.
            t.setValueOfTransaction(investmentAccount.getBalance());
            Account acc = new Account();
            acc.setId(investmentAccount.getAccountId());
            t.setAccount(acc);
            transactionService.insert(t);
        }

        return investmentAccountRes;

    }

    @Transactional(readOnly = true)
    public List<InvestmentAccountRes> findAll() throws JsonProcessingException {
        // Buscando todos os resultados cadastrados via request grpc
        InvestmentAccountListGRPCResponse response = investmentAccountGRPCBlockingStub.findAllInvestmentAccount(EmptyInvestmentAccountGRPCRequest.newBuilder().build());

        // Convertendo a lista de resposta do formato String para o Objeto de Investment Account do Java
        ArrayList<InvestmentAccountRes> listResponse = objectMapper.readValue(response.getInvestmentAccountList(), new TypeReference<ArrayList<InvestmentAccountRes>>(){});

        return listResponse;
    }


}
