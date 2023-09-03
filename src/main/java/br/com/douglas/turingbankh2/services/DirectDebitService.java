package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.*;
import br.com.douglas.turingbankh2.domain.enums.DirectDebitStatus;
import br.com.douglas.turingbankh2.domain.enums.DirectDebitType;
import br.com.douglas.turingbankh2.domain.enums.InstallmentStatus;
import br.com.douglas.turingbankh2.repositories.DirectDebitRepository;
import br.com.douglas.turingbankh2.repositories.InstallmentRepository;
import br.com.douglas.turingbankh2.repositories.LoanContractRepository;
import br.com.douglas.turingbankh2.requests.CustomerReq;
import br.com.douglas.turingbankh2.requests.DirectDebitReq;
import br.com.douglas.turingbankh2.requests.InstallmentReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.responses.DirectDebitRes;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class DirectDebitService {

    private final long FIVE_SECONDS = 5000;

    @Autowired
    private DirectDebitRepository repository;
    @Autowired
    private InstallmentRepository installmentRepository;
    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;



    @Transactional(readOnly = true)
    public List<DirectDebitRes> findAll() {
        List<DirectDebit> listOfDebits = repository.findAll();
        List<DirectDebitRes> listOfDebitsRes = listOfDebits.stream().map(x -> new DirectDebitRes(x)).collect(Collectors.toList());
        return listOfDebitsRes;
    }

    @Transactional(readOnly = true)
    public List<DirectDebitRes> findByAccountId(Long id) {
        List<DirectDebit> listOfDebits = repository.findByAccountId(id);
        List<DirectDebitRes> listOfDebitsRes = listOfDebits.stream().map(x -> new DirectDebitRes(x)).collect(Collectors.toList());
        return listOfDebitsRes;
    }

    @Transactional
    public DirectDebitRes update(Long id, DirectDebitReq directDebitReq) {
        try{
            DirectDebit entity = repository.getReferenceById(id);

            entity.setDirectDebitType(directDebitReq.getDirectDebitType());
            entity.setDescriptionOfDebit(directDebitReq.getDescriptionOfDebit());
            entity.setValueOfDebit(directDebitReq.getValueOfDebit());
            entity.setActualDueDate(directDebitReq.getActualDueDate());

            entity = repository.save(entity);
            return new DirectDebitRes(entity);
        } catch(javax.persistence.EntityNotFoundException e){
            throw new EntityNotFoundException("Não existe recurso com esse id para ser atualizado");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public DirectDebitRes insert(DirectDebitReq directDebitReq) {

        DirectDebit entity = new DirectDebit();

        entity.setDirectDebitType(directDebitReq.getDirectDebitType());
        entity.setDescriptionOfDebit(directDebitReq.getDescriptionOfDebit());


//        if(directDebitReq.getDirectDebitType().equals(DirectDebitType.EMPRESTIMO)){
//            LoanContract lc = loanContractRepository.findByAccountId(directDebitReq.getAccount().getId());
//            entity.setValueOfDebit(lc.getTotalContractAmount());
//        } else {
            entity.setValueOfDebit(directDebitReq.getValueOfDebit());
//        }

        entity.setDateOfAuthorizationOfDebit(LocalDate.now());
        entity.setInitialDueDate(directDebitReq.getInitialDueDate());
        entity.setActualDueDate(entity.getInitialDueDate());
        entity.setDirectDualDateStatus(directDebitReq.getDirectDualDateStatus());
        entity.setPaymentDate(null);
        entity.setAccount(directDebitReq.getAccount());

        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

        return new DirectDebitRes(entity);
    }

    @Scheduled(fixedDelay = FIVE_SECONDS)
    public void payDirectDebitLoanContracts(){

        System.out.println("Verificando contratos de empréstimos em débito automático...");

        // Obtém a lista com relação de débitos.
        List<DirectDebit> listOfDirectDebits = repository.findAll();

        // Para cada débito registrado.
        for(DirectDebit db : listOfDirectDebits){

            // Se o débito for do tipo empréstimo realiza a avaliação para o pagamento.
            if(db.getDirectDebitType().equals(DirectDebitType.EMPRESTIMO) && !db.getDirectDualDateStatus().equals(DirectDebitStatus.PAGO)){

                // Instancia o contrato de empréstimo associado com base no id da conta.
                LoanContract lc = loanContractRepository.findByAccountId(db.getAccount().getId());


                // Avalia e encerra o registro de débito automático para empréstimo se o contrato de empréstimo estiver inativo.
                if(lc.getLoanContractActive() == false){

                    Optional<DirectDebit> obj = repository.findById(db.getId());
                    DirectDebit directDebit = obj.orElseThrow(() -> new EntityNotFoundException("Débito não encontrada no banco de dados"));
                    directDebit.setDirectDualDateStatus(DirectDebitStatus.PAGO);
                    directDebit.setPaymentDate(LocalDate.now());
                    repository.save(directDebit);
                }

                // Checar se o empréstimo é do tipo débito automático.
                if(lc.getLoanContractDirectDebit() == true){
                    // Obtém a lista de parcelas associadas ao contrato de empréstimo.
                    List<Installment> listOfInstallments = installmentRepository.findAllByLoanContractId(lc.getId());

                    // Para cada parcela de empréstimo associada ao contrato:
                    for(Installment i : listOfInstallments){

                        if(i.getInstallmentStatus().equals(InstallmentStatus.PAGO)){
                            continue;
                        }

                        // Testa se a data atual é igual ou superior a data de vencimento atual do boleto
                        if(LocalDate.now().equals(db.getActualDueDate()) || LocalDate.now().isAfter(db.getActualDueDate())){

                            if(LocalDate.now().equals(i.getLimitDate()) || LocalDate.now().isAfter(ChronoLocalDate.from(i.getLimitDate()))){
                                // Se houver saldo na conta, executa o pagamento. Do contrário, realiza o reagendamento.
                                if(accountService.verifyBalance(i.getTotalValue(),db.getAccount().getId())){
                                    // Instancia uma nova transação
                                    TransactionReq t = new TransactionReq();
                                    t.setDescription("PAGAMENTO_PARCELA");
                                    t.setValueOfTransaction(i.getTotalValue());
                                    t.setAccount(lc.getAccount());
                                    // Envia para o registro da transação com atualização do saldo da conta e do status da parcela
                                    transactionService.paymentofInstallment(i.getId(),t);

                                } else {
                                    // Reagendamento do débito se não consegui pagar
                                    Optional<DirectDebit> obj = repository.findById(db.getId());
                                    DirectDebit directDebit = obj.orElseThrow(() -> new EntityNotFoundException("Débito não encontrada no banco de dados"));

                                    //LocalDate newLocalDate = db.getActualDueDate();
                                    LocalDate newLocalDate = LocalDate.now();

                                    if(newLocalDate.equals(DayOfWeek.SATURDAY)){
                                        newLocalDate = newLocalDate.plusDays(2);
                                    }

                                    if(newLocalDate.equals(DayOfWeek.SUNDAY)){
                                        newLocalDate = newLocalDate.plusDays(1);
                                    }

                                    newLocalDate = newLocalDate.plusDays(1);
                                    directDebit.setActualDueDate(newLocalDate);
                                    directDebit.setDirectDualDateStatus(DirectDebitStatus.REAGENDADO);
                                    repository.save(directDebit);
                                }

                            }


                            }
                        }
                    }
                }

            }

        }

    // Avalia a tabela de debito automático.
    // Se não for um contrato de empréstimo segue para o pagamento, do contrário utiliza outro método
    // avalia o saldo da conta
    // Se houver saldo, paga a conta, muda o status e debita do saldo.
    // Do contrário reagenda o pagamento para o próximo dia útil

    // Método de busca agendada e aplicação dos pagamentos
    @Scheduled(fixedDelay = FIVE_SECONDS)
    public void payCommonDirectDebits(){

        System.out.println("Verificando contas cadastradas em débito automático...");

        List<DirectDebit> listOfDirectDebits = repository.findAll();

        for(DirectDebit db : listOfDirectDebits){

            if(db.getDirectDebitType().equals(DirectDebitType.BOLETO) && !db.getDirectDualDateStatus().equals(DirectDebitStatus.PAGO)){

                if(LocalDate.now().equals(db.getActualDueDate()) || LocalDate.now().isAfter(db.getActualDueDate())){
                    if(accountService.verifyBalance(db.getValueOfDebit(),db.getAccount().getId())){
                        // Instancia uma nova transação
                        TransactionReq t = new TransactionReq();
                        t.setDescription("PAGAMENTO_BOLETO");
                        t.setValueOfTransaction(db.getValueOfDebit());
                        t.setAccount(db.getAccount());

                        transactionService.paymentOfDirectDebit(db.getId(),t);

                        Optional<DirectDebit> obj = repository.findById(db.getId());
                        DirectDebit directDebit = obj.orElseThrow(() -> new EntityNotFoundException("Débito não encontrada no banco de dados"));

                        directDebit.setPaymentDate(LocalDate.now());
                        directDebit.setDirectDualDateStatus(DirectDebitStatus.PAGO);
                        repository.save(directDebit);

                    } else {
                        Optional<DirectDebit> obj = repository.findById(db.getId());
                        DirectDebit directDebit = obj.orElseThrow(() -> new EntityNotFoundException("Débito não encontrada no banco de dados"));

                        //LocalDate newLocalDate = db.getActualDueDate();
                        LocalDate newLocalDate = LocalDate.now();
                        newLocalDate = newLocalDate.plusDays(1);

                        if(newLocalDate.equals(DayOfWeek.SATURDAY)){
                            newLocalDate = newLocalDate.plusDays(2);
                        }

                        if(newLocalDate.equals(DayOfWeek.SUNDAY)){
                            newLocalDate = newLocalDate.plusDays(1);
                        }

                        directDebit.setActualDueDate(newLocalDate);
                        directDebit.setDirectDualDateStatus(DirectDebitStatus.REAGENDADO);
                        repository.save(directDebit);
                    }
                }

            }

        }

    }
}
