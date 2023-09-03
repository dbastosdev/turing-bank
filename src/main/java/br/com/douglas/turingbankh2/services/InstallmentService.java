package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.Installment;
import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.repositories.LoanContractRepository;
import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstallmentService {

    @Autowired
    private InstallmentRepository repository;

    @Autowired
    private LoanContractRepository loanContractRepository;

    @Transactional(readOnly = true)
    public List<InstallmentRes> findAll(){
        //updateInstallmentsStatus();
        List<Installment> listInstallments = repository.findAll();
        List<InstallmentRes> listInstallmentsRes = listInstallments.stream().map(x -> new InstallmentRes(x)).collect(Collectors.toList());
        return listInstallmentsRes;
    }

    @Transactional(readOnly = true)
    public InstallmentRes findById(Long id) {
        updateInstallmentsStatus();
        Optional<Installment> obj = repository.findById(id);
        Installment installment = obj.orElseThrow(() -> new EntityNotFoundException("Parcela não encontrada no banco de dados"));
        return new InstallmentRes(installment);
    }

    public InstallmentRes insert(InstallmentReq installmentReq) {

        Installment entity = new Installment();

        entity.setLimitDate(installmentReq.getLimitDate());
        entity.setTotalValue(installmentReq.getTotalValue());
        entity.setPaymentDate(installmentReq.getPaymentDate());
        entity.setInstallmentStatus(installmentReq.getInstallmentStatus());
        entity.setLoanContract(installmentReq.getLoanContract());

        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

        return new InstallmentRes(entity);
    }

    @Transactional(readOnly = true)
    public List<InstallmentRes> allInstallmentsByLoanContract(Long id) {
        updateInstallmentsStatus();
        List<Installment> listOfInstallments = repository.findAllByLoanContractId(id);
        List<Installment> listOfInstallmentsModified = repository.findAllByLoanContractId(id);
        List<InstallmentRes> listInstallmentsRes = listOfInstallmentsModified.stream().map(x -> new InstallmentRes(x)).collect(Collectors.toList());
        return listInstallmentsRes;
    }

//    private void updateInstallmentsStatus(List<Installment> list){
//        for(Installment i:list){
//            //if(i.getLimitDate().isAfter(LocalDateTime.now())){
//            if(LocalDateTime.now().isAfter(i.getLimitDate())){
//                Installment entity = repository.getReferenceById(i.getId());
//                entity.setInstallmentStatus(InstallmentStatus.ATRASADO);
//                repository.save(entity);
//            }
//        }
//    }
    private void updateInstallmentsStatus(){
        List<Installment> list = repository.findAll();
        for(Installment i:list){
            if(LocalDateTime.now().isAfter(i.getLimitDate()) && i.getPaymentDate().equals(null)){
                Installment entity = repository.getReferenceById(i.getId());
                entity.setInstallmentStatus(InstallmentStatus.ATRASADO);
                repository.save(entity);
            }
        }
    }

    public void updateInstallmentByPayment(TransactionReq t, Long id){

        Optional<Installment> obj = repository.findById(id);
        Installment installment = obj.orElseThrow(() -> new EntityNotFoundException("Parcela não encontrada no banco de dados"));

        installment.setPaymentDate(LocalDateTime.now());
        installment.setInstallmentStatus(InstallmentStatus.PAGO);

        repository.save(installment);
    }
}
