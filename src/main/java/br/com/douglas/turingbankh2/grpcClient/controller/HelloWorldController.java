package br.com.douglas.turingbankh2.grpcClient.controller;

import br.com.douglas.turingbankh2.grpcClient.service.HelloWorldServiceClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {

    @Autowired
    private HelloWorldServiceClientImpl helloWorldServiceClient;

    @GetMapping
    public String getMessage() {
        return helloWorldServiceClient.getHelloWorldMessage().getMensagem();
    }
}
