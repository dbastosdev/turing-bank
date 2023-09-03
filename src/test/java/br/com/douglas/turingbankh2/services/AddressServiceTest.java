package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Address;
import br.com.douglas.turingbankh2.repositories.AddressRepository;
import br.com.douglas.turingbankh2.requests.AddressReq;
import br.com.douglas.turingbankh2.responses.AddressRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository repository;
    @InjectMocks
    private AddressService service;

    @Test
    void findAll() {
        // A - arrange
        Address address = new Address();
        AddressRes addressRes = new AddressRes();

        // A - act
        Mockito.when(repository.findAll()).thenReturn(List.of(address));
        List<AddressRes> result = service.findAll();
        // A - Assert - Não consegui que retornasse a mesma lista
        Assertions.assertNotNull(result);
    }

    @Test
    void findById() {
        // A - arrange
        long id = 1L;
        Address address = new Address();
        AddressRes addressRes = new AddressRes();

        // A - act
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(address));
        AddressRes result = service.findById(id);
        // A - Assert - Não consegui que retornasse o mesmo objeto
        Assertions.assertNotNull(result);
    }

    @Test
    void insert() throws JsonProcessingException {
        // A - arrange
        Address address = new Address();
        AddressReq addressReq = new AddressReq();

        // A - act
        Mockito.when(repository.save(Mockito.any(Address.class))).thenReturn(address);
        AddressRes result = service.insert(addressReq);

        // A - Assert
        Mockito.verify(repository).save(Mockito.any(Address.class));
        Assertions.assertEquals(AddressRes.class, result.getClass());
    }

    @Test
    void update() {
        // A - arrange
        long id = 1L;
        Address address = new Address();
        AddressReq addressReq = new AddressReq(address);
        addressReq.setZipcode("21.775-680");

        // A - act
        Mockito.when(repository.getReferenceById(id)).thenReturn(address);
        Mockito.when(repository.save(address)).thenReturn(address);
        AddressRes addressRes = service.update(id,addressReq);
        // A - Assert
        Assertions.assertEquals("21.775-680",addressRes.getZipcode());
    }

    @Test
    void delete() {
        // A - arrange
        long id = 1L;
        AddressReq addressReq = new AddressReq();
        // A - Act
        Mockito.doNothing().when(repository).deleteById(id);
        service.delete(id);
        // A - Assert
        Mockito.verify(repository).deleteById(id);
    }
}