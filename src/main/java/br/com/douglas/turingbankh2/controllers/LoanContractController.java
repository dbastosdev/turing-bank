package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.LoanContractReq;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.responses.LoanContractRes;
import br.com.douglas.turingbankh2.services.InstallmentService;
import br.com.douglas.turingbankh2.services.LoanContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/loan-contracts")
public class LoanContractController {

    @Autowired
    private LoanContractService service;

    @GetMapping
    public ResponseEntity<List<LoanContractRes>> findAll(){
        List<LoanContractRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<LoanContractRes> insert(@RequestBody LoanContractReq loanContractReq){
        LoanContractRes loanContractRes = service.insert(loanContractReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loanContractRes.getId()).toUri();
        return ResponseEntity.created(uri).body(loanContractRes);
    }

    @GetMapping(value = "by-account/{id}")
    public ResponseEntity<LoanContractRes> findLoanContractByAccount(@PathVariable Long id){
        LoanContractRes loanContractRes = service.findLoanContractByAccount(id);
        return ResponseEntity.ok().body(loanContractRes);
    }




}
