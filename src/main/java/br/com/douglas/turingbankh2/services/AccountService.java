package br.com.douglas.turingbankh2.services;

import br.com.douglas.turingbankh2.domain.Account;
import br.com.douglas.turingbankh2.domain.Customer;
import br.com.douglas.turingbankh2.domain.LoanContract;
import br.com.douglas.turingbankh2.domain.Transaction;
import br.com.douglas.turingbankh2.domain.enums.TransactionType;
import br.com.douglas.turingbankh2.repositories.AccountRepository;
import br.com.douglas.turingbankh2.repositories.CustomerRepository;
import br.com.douglas.turingbankh2.requests.AccountReq;
import br.com.douglas.turingbankh2.requests.LoanContractReq;
import br.com.douglas.turingbankh2.requests.TransactionReq;
import br.com.douglas.turingbankh2.responses.AccountRes;
import br.com.douglas.turingbankh2.responses.CustomerRes;
import br.com.douglas.turingbankh2.services.exceptions.EntityDuplicatedException;
import br.com.douglas.turingbankh2.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Transactional(readOnly = true)
    public List<AccountRes> findAll(){

        List<Account> listAccounts = repository.findAll();
        List<AccountRes> listAccountsRes = listAccounts.stream().map(x -> new AccountRes(x)).collect(Collectors.toList());
        return listAccountsRes;
    }

    @Transactional(readOnly = true)
    public AccountRes findById(Long id) {
        Optional<Account> obj = repository.findById(id);
        Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));
        return new AccountRes(account);
    }

    public AccountRes insert(AccountReq accountReq) {

        if(repository.existsByCustomer(accountReq.getCustomer())){
            throw new EntityDuplicatedException("Já há conta vinculada a este Cliente");
        } else {
            Account entity = new Account();
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(entity.getCreatedAt());
            entity.setBalance(accountReq.getInitialDeposit());
            //entity.setCustomer(new Customer()); // Funciona apenas quando se abre a conta e cria usuario ao mesmo tempo
            entity.setInitialDeposit(accountReq.getInitialDeposit());
            entity.setCreditLimit(accountReq.getCreditLimit());

            entity.setAccountNumber(accountNumberGenerator(accountReq));
            entity.setCustomer(accountReq.getCustomer());
            //entity.getCustomer().setId(null); // com essa linha funciona, mas cria novo recurso

            try{
                entity = repository.save(entity);
            }catch(RuntimeException e){
                throw new EntityDuplicatedException(e.getMessage());
            }


            return new AccountRes(entity);
        }
    }

    public String accountNumberGenerator(AccountReq acc){
        Instant now = Instant.now();
        return now.toString();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void updateBalance(TransactionReq t){
        if(t.getDescription().equals("DEPOSITO")){

            Optional<Account> obj = repository.findById(t.getAccount().getId());
            Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));


            Double actualBalance = account.getBalance();
            actualBalance += t.getValueOfTransaction();
            account.setBalance(actualBalance);
            repository.save(account);
       }

        if(t.getDescription().equals("SAQUE") || t.getDescription().equals("PAGAMENTO_PARCELA") || t.getDescription().equals("PAGAMENTO_BOLETO")){

            Optional<Account> obj = repository.findById(t.getAccount().getId());
            Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));


            Double actualBalance = account.getBalance();
            actualBalance -= t.getValueOfTransaction();

            if(actualBalance < 0){
                throw new EntityNotFoundException("Saldo insuficiente para realização da operação de saque");
            }

            account.setBalance(actualBalance);
            repository.save(account);
        }
    }

    public void updateBalanceByLoanContract(LoanContractReq lc){

        Optional<Account> obj = repository.findById(lc.getAccount().getId());
        Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));

        Double actualBalance = account.getBalance();
        actualBalance += lc.getRequestedAmount();
        account.setBalance(actualBalance);
        repository.save(account);
    }

    public boolean verifyBalance(Double totalValueOfDebit, Long idOfAccount){

        Optional<Account> obj = repository.findById(idOfAccount);
        Account account = obj.orElseThrow(() -> new EntityNotFoundException("Conta não encontrada no banco de dados"));

        if(totalValueOfDebit < account.getBalance()){
            return true;
        } else {
            return false;
        }


    }
}
