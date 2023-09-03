package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
import br.com.douglas.turingbankh2.services.InstallmentService;
import br.com.douglas.turingbankh2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/installments")
public class InstallmentController {

    @Autowired
    private InstallmentService service;

    @GetMapping
    public ResponseEntity<List<InstallmentRes>> findAll(){
        List<InstallmentRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InstallmentRes> findById(@PathVariable Long id){
       InstallmentRes installmentRes = service.findById(id);
        return ResponseEntity.ok().body(installmentRes);
    }

//    @PostMapping
//    public ResponseEntity<InstallmentRes> insert(@RequestBody InstallmentReq installmentReq){
//        InstallmentRes installmentRes = service.insert(installmentReq);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(installmentRes.getId()).toUri();
//        return ResponseEntity.created(uri).body(installmentRes);
//    }

    @GetMapping(value = "by-contract/{id}")
    public ResponseEntity<List<InstallmentRes>> findAllByAccount(@PathVariable Long id){
        List<InstallmentRes> list = service.allInstallmentsByLoanContract(id);
        return ResponseEntity.ok().body(list);
    }




}
