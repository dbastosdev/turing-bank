package br.com.douglas.turingbankh2.grpcClient.service;

import br.com.douglas.turingbankh2.protofiles.Empty;
import br.com.douglas.turingbankh2.protofiles.HelloWorldResponse;
import br.com.douglas.turingbankh2.protofiles.HelloWorldServiceGRPCGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceClientImpl {

    @GrpcClient("turingbank")
    private HelloWorldServiceGRPCGrpc.HelloWorldServiceGRPCBlockingStub helloWorldServiceGRPCBlockingStub;

    public HelloWorldResponse getHelloWorldMessage() {
        HelloWorldResponse response = helloWorldServiceGRPCBlockingStub.getHelloWorld(Empty.newBuilder().build());
        return response;
    }
}
