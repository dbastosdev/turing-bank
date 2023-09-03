package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.requests.AddressReq;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.AddressRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.services.AddressService;
import br.com.douglas.turingbankh2.services.InstallmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping
    public ResponseEntity<List<AddressRes>> findAll(){
        List<AddressRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressRes> findById(@PathVariable Long id){
        AddressRes addressRes = service.findById(id);
        return ResponseEntity.ok().body(addressRes);
    }

    @PostMapping
    public ResponseEntity<AddressRes> insert(@RequestBody AddressReq addressReq) throws JsonProcessingException {
        AddressRes addressRes = service.insert(addressReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addressRes.getId()).toUri();
        return ResponseEntity.created(uri).body(addressRes);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressRes> update(@PathVariable Long id,@RequestBody AddressReq addressReq){
        AddressRes addressRes = service.update(id, addressReq);
        return ResponseEntity.ok().body(addressRes);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AddressRes> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}
