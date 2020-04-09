package com.base.ebank.services;

import com.base.ebank.bindingModels.BankAccountBindingModel;
import com.base.ebank.entities.BankAccount;
import com.base.ebank.entities.User;
import com.base.ebank.repositories.BankAccountRepository;
import com.base.ebank.repositories.TransactionRepository;
import com.base.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
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
}


