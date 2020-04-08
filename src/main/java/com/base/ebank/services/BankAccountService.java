package com.base.ebank.services;

import com.base.ebank.entities.BankAccount;
import com.base.ebank.repositories.BankAccountRepository;
import com.base.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Set;

@Service
@Transactional
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public Set<BankAccount> findAllByOwnerUsername(Principal principal) {
        Set<BankAccount> bankAccounts = this.bankAccountRepository
                .findAllByOwnerUsername(principal.getName());

        return bankAccounts;
    }

public boolean 
}


