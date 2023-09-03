package br.com.douglas.turingbankh2.domain;

import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

// Base: https://www.alura.com.br/artigos/agendando-tarefas-com-scheduled-do-spring

@Component
//@EnableScheduling
public class TestCron {
    private final long THIRD_SECONDS = 30000;
    private final long FIVE_SECONDS = 5000;
    private final long TEN_SECONDS = 10000;

    @Autowired
    private CustomerService service;

    @Autowired
    private InstallmentRepository installmentRepository;

//    @Scheduled(fixedDelay = FIVE_SECONDS)
    public void seedOfDataBaseEachSecondFiveSeconds() throws JsonProcessingException {

        CustomerReq c = new CustomerReq();
        c.setName("Dark");
        c.setCpf("123.321.341-34"+LocalDateTime.now().toString());
        service.insert(c);

    }

//    @Scheduled(fixedDelay = TEN_SECONDS)
    public void printListOfCustomersEachTenSeconds() {

        List<CustomerRes> list = service.findAll();

        for(CustomerRes element : list){
            System.out.println(element);
        }

        System.out.println("=== === === ==== ==== ====");
    }

//    @Scheduled(fixedDelay = FIVE_SECONDS)
    public void testOfGetAllInstallments() {

        List<Installment> list = installmentRepository.findAll();

        for(Installment element : list){
            System.out.println(element);
        }

        System.out.println("=== === TENTEI BUSCAR INSTALLMENTS ==== ====");
    }


}
