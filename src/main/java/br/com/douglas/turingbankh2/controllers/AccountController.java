package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.requests.AccountReq;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.AccountService;
import br.com.douglas.turingbankh2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping
    public ResponseEntity<List<AccountRes>> findAll(){
        List<AccountRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountRes> findById(@PathVariable Long id){
        AccountRes accountRes = service.findById(id);
        return ResponseEntity.ok().body(accountRes);
    }

    @PostMapping
    public ResponseEntity<AccountRes> insert(@RequestBody AccountReq accountReq){
        AccountRes accountRes = service.insert(accountReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(accountRes.getId()).toUri();
        return ResponseEntity.created(uri).body(accountRes);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomerRes> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
