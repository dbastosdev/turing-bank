package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.requests.AccountReq;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
import br.com.douglas.turingbankh2.services.AccountService;
import br.com.douglas.turingbankh2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<List<TransactionRes>> findAll(){
        List<TransactionRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionRes> findById(@PathVariable Long id){
        TransactionRes transactionRes = service.findById(id);
        return ResponseEntity.ok().body(transactionRes);
    }

    @PostMapping
    public ResponseEntity<TransactionRes> insert(@RequestBody TransactionReq transactionReq){
        TransactionRes transactionRes = service.insert(transactionReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionRes.getId()).toUri();
        return ResponseEntity.created(uri).body(transactionRes);
    }

    @PostMapping(value = "installment/{id}")
    public ResponseEntity<TransactionRes> paymentOfInstallment(@PathVariable Long id,@RequestBody TransactionReq transactionReq){
        TransactionRes transactionRes = service.paymentofInstallment(id, transactionReq);
        return ResponseEntity.ok().body(transactionRes);
    }

    @GetMapping(value = "account/{id}")
    public ResponseEntity<List<TransactionRes>> findAllByAccount(@PathVariable Long id){
        List<TransactionRes> list = service.allTransactionByAccount(id);
        return ResponseEntity.ok().body(list);
    }




}
