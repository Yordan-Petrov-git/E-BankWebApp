package com.base.ebank.services;

import com.base.ebank.bindingModels.BankAccountBindingModel;
import com.base.ebank.entities.BankAccount;
import com.base.ebank.entities.Transaction;
import com.base.ebank.entities.User;
import com.base.ebank.repositories.BankAccountRepository;
import com.base.ebank.repositories.TransactionRepository;
import com.base.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Set<BankAccount> findAllByOwnerUsername(Principal principal) {
        Set<BankAccount> bankAccounts = this.bankAccountRepository
                .findAllByOwnerUsername(principal.getName());

        return bankAccounts;
    }

    public boolean createAccount(BankAccountBindingModel model) {

        BankAccount account = this.bankAccountRepository.findByIban(model.getIban());
        if (account != null) return false;
        account = new BankAccount();
        User user = this.userRepository.findByUsername(model.getUsername());
        if (user == null) return false;
        account.setIban(model.getIban());
        account.setOwner(user);
        account.setBalance(BigDecimal.ZERO);
        this.bankAccountRepository.saveAndFlush(account);
        return true;
    }

    public BankAccountBindingModel extractAccountForTransaction(Long id) {
        BankAccount bankAccount = this.bankAccountRepository.findById(id).orElse(null);

        if (bankAccount == null) {
            throw new IllegalArgumentException("Invalid Bank account !");
        }

        BankAccountBindingModel bankAccountBindingModel = new BankAccountBindingModel();
        bankAccountBindingModel.setId(id);
        bankAccountBindingModel.setUsername(bankAccount.getOwner().getUsername());
        bankAccountBindingModel.setIban(bankAccount.getIban());
        return bankAccountBindingModel;
    }

    public boolean depositAmount(BankAccountBindingModel bankAccountBindingModel) {
        BankAccount bankAccount = this.bankAccountRepository
                .findById(bankAccountBindingModel.getId()).orElse(null);

        if (bankAccount == null) {
            return false;

        } else if (BigDecimal.ZERO.compareTo(bankAccountBindingModel.getAmount()) > 0) {
            return false;
        }
        bankAccount.setBalance(bankAccount.getBalance().add(bankAccountBindingModel.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setFromAccount(bankAccount);
        transaction.setToAccount(bankAccount);
        transaction.setAmount(bankAccountBindingModel.getAmount());
        this.transactionRepository.save(transaction);
        this.bankAccountRepository.save(bankAccount);
        return true;

    }


    public boolean withdrawAmount(BankAccountBindingModel bankAccountBindingModel) {
        BankAccount bankAccount = this.bankAccountRepository
                .findById(bankAccountBindingModel.getId()).orElse(null);

        if (bankAccount == null) {
            return false;
        } else if (bankAccountBindingModel.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        } else if (bankAccount.getBalance().compareTo(bankAccountBindingModel.getAmount()) < 0) {
            //remove to get negative amount
            return false;
        }
        bankAccount.setBalance(bankAccount.getBalance().subtract(bankAccountBindingModel.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAW");
        transaction.setFromAccount(bankAccount);
        transaction.setToAccount(bankAccount);
        transaction.setAmount(bankAccountBindingModel.getAmount());
        this.transactionRepository.save(transaction);
        this.bankAccountRepository.save(bankAccount);
        return true;

    }


    public boolean transferAmount(BankAccountBindingModel model) {
        BankAccount fromAccount = this.bankAccountRepository
                .findById(model.getId()).orElse(null);


        BankAccount toAccount = this.bankAccountRepository
                .findById(model.getReceiverId()).orElse(null);


        if (fromAccount == null || toAccount == null) {
            return false;
        } else if (model.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (fromAccount.getBalance().compareTo(model.getAmount()) < 0) {
            return false;
        }


        BigDecimal newBalance = toAccount.getBalance().add(model.getAmount());

        toAccount.setBalance(newBalance);

        fromAccount.setBalance(fromAccount.getBalance().subtract(model.getAmount()));


        Transaction transaction = new Transaction();

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        transaction.setAmount(model.getAmount());

        transaction.setType("TRANSFER");

        this.transactionRepository.save(transaction);
        this.bankAccountRepository.save(fromAccount);
        this.bankAccountRepository.save(toAccount);
        return true;
    }
    public List<BankAccount> getAllById(Long id){
        return this.bankAccountRepository.findAllByIdIsNot(id);
    }
}


