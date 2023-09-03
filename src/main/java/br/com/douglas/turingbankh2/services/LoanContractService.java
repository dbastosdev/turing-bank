package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.*;
import br.com.douglas.turingbankh2.domain.enums.*;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.repositories.LoanContractRepository;
import br.com.douglas.turingbankh2.requests.DirectDebitReq;
import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.LoanContractReq;
import br.com.douglas.turingbankh2.responses.DirectDebitRes;
import br.com.douglas.turingbankh2.responses.LoanContractRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class LoanContractService {

    private final long FIVE_SECONDS = 5000;

    @Autowired
    private LoanContractRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private InstallmentService installmentService;

    @Autowired
    private DirectDebitService directDebitService;

    @Transactional(readOnly = true)
    public List<LoanContractRes> findAll(){
        //updateLoanContractStatus();
        List<LoanContract> listLoanContracts = repository.findAll();
        List<LoanContractRes> listLoanContractsRes = listLoanContracts.stream().map(x -> new LoanContractRes(x)).collect(Collectors.toList());
        return listLoanContractsRes;
    }

    public LoanContractRes insert(LoanContractReq loanContractReq) {

        Optional<Account> obj = accountRepository.findById(loanContractReq.getAccount().getId());
        Account account = obj.orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada no banco de dados"));

        if(loanContractReq.getRequestedAmount() > account.getCreditLimit()){
            throw new EntityDuplicatedException("Limite de crédito é insuficiente para contratação de empréstimo seu limite: " + account.getCreditLimit());
        }

        LoanContract loanContract = repository.findByAccountId(loanContractReq.getAccount().getId());

        if(repository.existsByAccount(loanContractReq.getAccount()) && loanContract.getLoanContractActive()){
            throw new EntityDuplicatedException("Já há contrato de empréstimo associado a esta conta de cliente");
        }

        accountService.updateBalanceByLoanContract(loanContractReq);

        LoanContract entity = new LoanContract();

        entity.setDirectDebit(loanContractReq.getDirectDebit());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setRequestedAmount(loanContractReq.getRequestedAmount());
        entity.setMonthlyFee(calcMonthlyFee(loanContractReq.getContractType()));
        entity.setNumberOfInstallments(loanContractReq.getNumberOfInstallments());
        entity.setAnnualFee(calcAnnualFee(entity.getMonthlyFee()));
        entity.setTotalContractAmount(calcTotalContractAmount(entity.getMonthlyFee(),entity.getRequestedAmount(), entity.getNumberOfInstallments()));
        entity.setContractStatus(LoanContractStatus.EM_DIA);
        entity.setContractType(loanContractReq.getContractType());
        entity.setAccount(loanContractReq.getAccount());
        entity.setLoanContractActive(loanContractReq.getLoanContractActive());
        entity.setLoanContractDirectDebit(loanContractReq.getLoanContractDirectDebit());
        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

        createInstallments(loanContractReq.getNumberOfInstallments(), entity);

        if(entity.getLoanContractDirectDebit() == true){
            createLoanContractDirectDebit(entity);
        }

        return new LoanContractRes(entity);
    }

    private void createLoanContractDirectDebit(LoanContract loanContract) {

        DirectDebitReq entity = new DirectDebitReq();

        entity.setDirectDebitType(DirectDebitType.EMPRESTIMO);
        entity.setDescriptionOfDebit("Contrato de empréstimo: " + loanContract.getId() + " Parcelas fixas: " + loanContract.getNumberOfInstallments());
        entity.setValueOfDebit(loanContract.getTotalContractAmount());
        entity.setDateOfAuthorizationOfDebit(LocalDate.now());
        entity.setInitialDueDate(LocalDate.from(loanContract.getCreatedAt().plusMonths(1)));
        entity.setActualDueDate(entity.getInitialDueDate());
        entity.setDirectDualDateStatus(DirectDebitStatus.AGENDADO);
        entity.setPaymentDate(null);
        entity.setAccount(loanContract.getAccount());

        try{
           directDebitService.insert(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

    }

    private void createInstallments(Integer numberOfInstallments, LoanContract entity) {

        Double installmentValue = calcInstallment(entity.getTotalContractAmount(),numberOfInstallments);

        for(int i = 1; i <= numberOfInstallments; i++){
            InstallmentReq installment = new InstallmentReq();
            installment.setLimitDate(entity.getCreatedAt().plusMonths(i));
            installment.setTotalValue(installmentValue);
            installment.setPaymentDate(null);
            installment.setInstallmentStatus(InstallmentStatus.AGUARDANDO_PAGAMENTO);
            installment.setLoanContract(entity);

            installmentService.insert(installment);
        }
    }

    @Transactional(readOnly = true)
    public LoanContractRes findLoanContractByAccount(Long id) {
       // updateLoanContractStatus();
       LoanContract loanContract = repository.findByAccountId(id);
       LoanContractRes loanContractRes = new LoanContractRes(loanContract);
        return loanContractRes;
    }

    // Base da regra: https://blog.aprovatotal.com.br/juros-compostos-como-calcular-formula-e-exemplo/
    private Double calcTotalContractAmount(Double monthlyFee, Double requestedAmout, Integer numberOfInstallments) {

        Double installmentWithoutFee = requestedAmout / numberOfInstallments;
        Double monthlyInstallmentwithFee = installmentWithoutFee;
        Double acc = 0.0;

        for(int i = 0; i < numberOfInstallments; i++){
            monthlyInstallmentwithFee = monthlyInstallmentwithFee * (1 + (monthlyFee/100));
            acc += monthlyInstallmentwithFee;
            //System.out.println(monthlyInstallmentwithFee);
        }

        return acc;
    }

    // Base: https://clubedospoupadores.com/conversor-de-taxas-de-juros-mensal-para-anual
    private Double calcAnnualFee(Double monthlyFee) {
        return (Math.pow((1 + monthlyFee/100),12) - 1)*100;
    }

    private Double calcInstallment(Double totalContractAmount, Integer numberOfInstallments) {
        return Math.floor(totalContractAmount / numberOfInstallments);
    }

    private Double calcMonthlyFee(ContractType contractType) {
        switch (contractType){
            case PESSOAL -> {
                return 3.8;
            }
            case IMOBILIARIO -> {
                return 2.5;
            }
            case AUTO -> {
                return 2.0;
            }
        }
        return null;
    }

    @Scheduled(fixedDelay = FIVE_SECONDS)
    @Transactional
    public void updateLoanContractStatus(){
        List<LoanContract> list = repository.findAll();
        for(LoanContract lc:list){

            List<Installment> listInstallment = installmentRepository.findAllByLoanContractId(lc.getId());

            Integer numberOfInstallments = lc.getNumberOfInstallments();
            Integer nummberOfPayedInstallments = 0;

            for(Installment i:listInstallment){
                if(i.getInstallmentStatus().equals(InstallmentStatus.ATRASADO)){
                    LoanContract entity = repository.getReferenceById(lc.getId());
                    entity.setContractStatus(LoanContractStatus.ATRASADO);
                    repository.save(entity);
                    //break;
                }
                if(i.getInstallmentStatus().equals(InstallmentStatus.PAGO)){
                    nummberOfPayedInstallments++;
                }
            }

            if(numberOfInstallments == nummberOfPayedInstallments){

                Long id = lc.getId();
                update(id);
            }

        }
    }

    public void update(Long id) {
        try{
            LoanContract entity = repository.getReferenceById(id);

            entity.setLoanContractActive(Boolean.FALSE);
            entity.setContractStatus(LoanContractStatus.FINALIZADO);

            repository.save(entity);
        } catch(javax.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Não existe recurso com esse id para ser atualizado");
        }
    }
}
