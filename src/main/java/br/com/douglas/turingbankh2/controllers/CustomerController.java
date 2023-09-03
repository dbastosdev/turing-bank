package br.com.douglas.turingbankh2.controllers;

import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerRes>> findAll(){
        List<CustomerRes> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerRes> findById(@PathVariable Long id){
        CustomerRes customerRes = service.findById(id);
        return ResponseEntity.ok().body(customerRes);
    }

    @PostMapping
    public ResponseEntity<CustomerRes> insert(@RequestBody CustomerReq customerReq) throws JsonProcessingException {
        CustomerRes customerRes = service.insert(customerReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerRes.getId()).toUri();
        return ResponseEntity.created(uri).body(customerRes);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerRes> update(@PathVariable Long id,@RequestBody CustomerReq customerReq){
        CustomerRes customerRes = service.update(id, customerReq);
        return ResponseEntity.ok().body(customerRes);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomerRes> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
