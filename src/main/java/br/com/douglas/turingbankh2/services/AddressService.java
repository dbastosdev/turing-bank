package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.repositories.AddressRepository;
import br.com.douglas.turingbankh2.requests.AddressReq;
import br.com.douglas.turingbankh2.responses.AddressRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Transactional(readOnly = true)
    public List<AddressRes> findAll(){
        List<Address> addresses = repository.findAll();
        List<AddressRes> listOfAddressRes = addresses.stream().map(x -> new AddressRes(x)).collect(Collectors.toList());
        return listOfAddressRes;
    }

    @Transactional(readOnly = true)
    public AddressRes findById(Long id) {
        Optional<Address> obj = repository.findById(id);
        Address address = obj.orElseThrow(() -> new EntityNotFoundException("Parcela não encontrada no banco de dados"));
        return new AddressRes(address);
    }

    public AddressRes insert(AddressReq addressReq) throws JsonProcessingException {

        if(repository.existsByCustomerForAddress(addressReq.getCustomerForAddress())){
            throw new EntityDuplicatedException("Já há endereço vinculado a este Cliente");
        }

        // Rest template
        // https://www.baeldung.com/rest-template
        // https://javahowtos.com/guides/107-spring/363-how-to-add-headers-to-resttemplate-in-spring.html
        // https://salesforce.stackexchange.com/questions/307659/unexpected-character-code-60-expected-a-valid-value-number-string-ar

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String baseUrl = "http://cep.la/";
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + addressReq.getZipcode(), HttpMethod.GET, httpEntity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        JsonNode fu = root.path("uf");
        JsonNode city = root.path("cidade");
        JsonNode district = root.path("bairro");
        JsonNode street = root.path("logradouro");

        Address entity = new Address();

        entity.setZipcode(addressReq.getZipcode());
        entity.setFu(fu.asText());
        entity.setCity(city.asText());
        entity.setNumber(addressReq.getNumber());
        entity.setDistrict(district.asText());
        entity.setStreet(street.asText());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(entity.getCreatedAt());
        entity.setCustomerForAddress(addressReq.getCustomerForAddress());

        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

        return new AddressRes(entity);
    }

    @Transactional
    public AddressRes update(Long id, AddressReq addressReq) {
        try{
            Address address = repository.getReferenceById(id);

            address.setFu(addressReq.getFu());
            address.setCity(addressReq.getCity());
            address.setUpdatedAt(LocalDateTime.now());
            address.setDistrict(addressReq.getDistrict());
            address.setNumber(addressReq.getNumber());
            address.setStreet(addressReq.getStreet());
            address.setZipcode(addressReq.getZipcode());

            address = repository.save(address);
            return new AddressRes(address);
        } catch(javax.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Não existe recurso com esse id para ser atualizado");
        }


    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
