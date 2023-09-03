package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.requests.DirectDebitReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.DirectDebitRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
import br.com.douglas.turingbankh2.services.DirectDebitService;
import br.com.douglas.turingbankh2.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/direct-debit")
public class DirectDebitController {

    @Autowired
    private DirectDebitService service;

    @GetMapping
    public ResponseEntity<List<DirectDebitRes>> findAll(){
        List<DirectDebitRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/by-account/{id}")
    public ResponseEntity<List<DirectDebitRes>> findByAccountId(@PathVariable Long id){
        List<DirectDebitRes> list = service.findByAccountId(id);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<DirectDebitRes> insert(@RequestBody DirectDebitReq directDebitReq){
        DirectDebitRes directDebitRes = service.insert(directDebitReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(directDebitRes.getId()).toUri();
        return ResponseEntity.created(uri).body(directDebitRes);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DirectDebitRes> update(@PathVariable Long id,@RequestBody DirectDebitReq directDebitReq){
        DirectDebitRes directDebitRes = service.update(id, directDebitReq);
        return ResponseEntity.ok().body(directDebitRes);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DirectDebitRes> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }



}
