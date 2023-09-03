package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.repositories.CustomerRepository;
import br.com.douglas.turingbankh2.requests.AddressReq;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.AddressRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private AddressService addressService;


    @Transactional(readOnly = true)
    public List<CustomerRes> findAll(){

        List<Customer> listCustomers = repository.findAll();
        List<CustomerRes> listCustomersRes = listCustomers.stream().map(x -> new CustomerRes(x)).collect(Collectors.toList());
        return listCustomersRes;
    }

    @Transactional(readOnly = true)
    public CustomerRes findById(Long id) {
        Optional<Customer> obj = repository.findById(id);
        Customer customer = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada no banco de dados"));
        return new CustomerRes(customer);
    }

    public CustomerRes insert(CustomerReq customerReq) throws JsonProcessingException {

        if(repository.existsByCpf(customerReq.getCpf())){
            throw new EntityDuplicatedException("Já existe recurso criado com esse cpf");
        } else {
            Customer entity = new Customer();
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(entity.getCreatedAt());
            entity.setName(customerReq.getName());
            entity.setLogin(customerReq.getLogin());
            entity.setPassword(customerReq.getPassword());
            entity.setCpf(customerReq.getCpf());
            entity = repository.save(entity);

            if(customerReq.getZipcode() != null && customerReq.getNumber() != null){

                AddressReq addressReq = new AddressReq();

                addressReq.setZipcode(customerReq.getZipcode());
                addressReq.setNumber(customerReq.getNumber());
                addressReq.setCustomerForAddress(entity);

                AddressRes addressRes = addressService.insert(addressReq);

                entity.setAddress(new Address(addressRes));
            }



            return new CustomerRes(entity);
        }
    }

    @Transactional
    public CustomerRes update(Long id, CustomerReq customerReq) {

        try{
            Customer entity = repository.getReferenceById(id);
            entity.setUpdatedAt(LocalDateTime.now());
            entity.setPassword(customerReq.getPassword());
            entity.setLogin(customerReq.getLogin());
            entity.setName(customerReq.getName());
            entity.setCpf(customerReq.getCpf());
            entity = repository.save(entity);
            return new CustomerRes(entity);
        } catch(javax.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Não existe recurso com esse id para ser atualizado");
        }


    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
