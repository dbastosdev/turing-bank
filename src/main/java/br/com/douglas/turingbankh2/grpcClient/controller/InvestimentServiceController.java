package br.com.douglas.turingbankh2.grpcClient.controller;

import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountReq;
import br.com.douglas.turingbankh2.grpcClient.mappers.InvestmentAccountRes;
import br.com.douglas.turingbankh2.grpcClient.service.InvestmentServiceClient;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/internal-client-investment")
public class InvestimentServiceController {

    @Autowired
    private InvestmentServiceClient investmentAccountServiceClient;

    @GetMapping(value = "/{id}")
    public InvestmentAccountRes findInvestmentAccount(@PathVariable Long id) throws JsonProcessingException {
        return investmentAccountServiceClient.findInvestmentAccountsById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteInvestmentAccountsById(@PathVariable Long id) throws JsonProcessingException {
        investmentAccountServiceClient.deleteInvestmentAccountsById(id);
    }

    @PostMapping
    public ResponseEntity<InvestmentAccountRes> insert(@RequestBody InvestmentAccountReq investmentAccountReq) throws JsonProcessingException {
        InvestmentAccountRes investmentAccountRes = investmentAccountServiceClient.insert(investmentAccountReq);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(investmentAccountRes.getId()).toUri();
        return ResponseEntity.created(uri).body(investmentAccountRes);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<InvestmentAccountRes> withdraw(@PathVariable Long id) throws JsonProcessingException {
        InvestmentAccountRes investmentAccountRes = investmentAccountServiceClient.withdraw(id);
        return ResponseEntity.ok().body(investmentAccountRes);
    }

    @GetMapping
    public ResponseEntity<List<InvestmentAccountRes>> findAll() throws JsonProcessingException {
        List<InvestmentAccountRes> list = investmentAccountServiceClient.findAll();
        return ResponseEntity.ok().body(list);
    }


}
