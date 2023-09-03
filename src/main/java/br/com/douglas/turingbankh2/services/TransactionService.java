package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.repositories.LoanContractRepository;
import br.com.douglas.turingbankh2.repositories.TransactionRepository;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.InstallmentRes;
import br.com.douglas.turingbankh2.responses.TransactionRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoanContractRepository loanContractRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private InstallmentService installmentService;


    @Transactional(readOnly = true)
    public List<TransactionRes> findAll(){

        List<Transaction> listTransactions = repository.findAll();
        List<TransactionRes> listTransactionRes = listTransactions.stream().map(x -> new TransactionRes(x)).collect(Collectors.toList());
        return listTransactionRes;
    }

    @Transactional(readOnly = true)
    public TransactionRes findById(Long id) {
        Optional<Transaction> obj = repository.findById(id);
        Transaction transaction = obj.orElseThrow(() -> new EntityNotFoundException("Transação não encontrada no banco de dados"));
        return new TransactionRes(transaction);
    }

    // Para saque e depósito
    public TransactionRes insert(TransactionReq transactionReq) {

            Transaction entity = new Transaction();
            entity.setBalanceBeforeTransaction(getBalance(transactionReq));

            accountService.updateBalance(transactionReq);

            entity.setBalanceAfterTransaction(getBalance(transactionReq));

            entity.setCreatedAt(LocalDateTime.now());
            entity.setDescription(transactionReq.getDescription());
            entity.setValueOfTransaction(transactionReq.getValueOfTransaction());
            entity.setAccount(transactionReq.getAccount());

            try{
                entity = repository.save(entity);
            }catch(RuntimeException e){
                throw new EntityDuplicatedException(e.getMessage());
            }


            return new TransactionRes(entity);
    }

    // Para pagamento de parcela de empréstimo
    public TransactionRes paymentofInstallment(Long id, TransactionReq transactionReq) {

        Transaction entity = new Transaction();
        entity.setBalanceBeforeTransaction(getBalance(transactionReq));

        accountService.updateBalance(transactionReq);

        entity.setBalanceAfterTransaction(getBalance(transactionReq));

        installmentService.updateInstallmentByPayment(transactionReq, id);

        entity.setCreatedAt(LocalDateTime.now());
        entity.setDescription(transactionReq.getDescription() +
                " - Parcela: " + id +
                " Conta debitada: " + getAccountNumber(transactionReq.getAccount().getId()) +
                " Contrato de empréstimo: " + getLoanContractNumber(id));
        entity.setValueOfTransaction(transactionReq.getValueOfTransaction());
        entity.setAccount(transactionReq.getAccount());

        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }

        return new TransactionRes(entity);
    }

    // Para pagamento de conta em débito automático

    private String getLoanContractNumber(Long id) {
        InstallmentRes installment = installmentService.findById(id);
        return installment.getLoanContract().getId().toString();
    }

    private String getAccountNumber(Long id) {
        Optional<Account> op = accountRepository.findById(id);
        Account acc = op.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));
        return acc.getAccountNumber();
    }

    @Transactional(readOnly = true)
    public List<TransactionRes> allTransactionByAccount(Long id) {
        List<Transaction> listOfTransactions = repository.findAllByAccountId(id);
        List<TransactionRes> listTransactionRes = listOfTransactions.stream().map(x -> new TransactionRes(x)).collect(Collectors.toList());
        return listTransactionRes;
    }

    // Regra de negócio exclusivo da transação.
    private Double getBalance(TransactionReq transactionReq){
        Optional<Account> obj = accountRepository.findById(transactionReq.getAccount().getId());
        Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));
        Double balance = account.getBalance();
        return balance;
    }

    public void paymentOfDirectDebit(Long id, TransactionReq t) {
        Transaction entity = new Transaction();
        entity.setBalanceBeforeTransaction(getBalance(t));

        accountService.updateBalance(t);

        entity.setBalanceAfterTransaction(getBalance(t));

        entity.setCreatedAt(LocalDateTime.now());
        entity.setDescription(t.getDescription() +
                " Conta debitada: " + t.getAccount().getAccountNumber() +
                " Valor: " + t.getValueOfTransaction());
        entity.setValueOfTransaction(t.getValueOfTransaction());
        entity.setAccount(t.getAccount());

        try{
            entity = repository.save(entity);
        }catch(RuntimeException e){
            throw new EntityDuplicatedException(e.getMessage());
        }
    }
}
