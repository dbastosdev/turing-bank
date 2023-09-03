package br.com.douglas.turingbankh2.grpcserver;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.protofiles.*;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.services.AccountService;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class HelloWorldServiceImpl extends HelloWorldServiceGRPCGrpc.HelloWorldServiceGRPCImplBase {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void getHelloWorld(Empty request, StreamObserver<HelloWorldResponse> responseObserver) {
        try {
            HelloWorldResponse resp = HelloWorldResponse.newBuilder()
                    .setMensagem("Hello World!")
                    .build();
            responseObserver.onNext(resp);
        } catch (EntityNotFoundException e) {
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void findAccountById(FindAccountByIdGRPCRequest request, StreamObserver<FindAccountByIdGRPCResponse> responseObserver) {
        try {
            AccountRes accountRes = accountService.findById(request.getId());
            FindAccountByIdGRPCResponse resp = FindAccountByIdGRPCResponse.newBuilder()
                    .setAccount(objectMapper.writeValueAsString(accountRes))
                    .build();
            responseObserver.onNext(resp);
        } catch (JsonProcessingException | EntityNotFoundException e) {
            responseObserver.onError(e);
        }
        responseObserver.onCompleted();
    }
}
