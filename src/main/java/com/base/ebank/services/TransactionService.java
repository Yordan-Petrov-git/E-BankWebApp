package com.base.ebank.services;

import com.base.ebank.entities.Transaction;
import com.base.ebank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Set<Transaction> getAllByName(String name) {
        return this.transactionRepository.findAll()
                .stream()
                .filter(t -> t.getToAccount().getOwner().getUsername().equals(name) ||
                        t.getFromAccount().getOwner().getUsername().equals(name))
                .sorted(Comparator.comparing(Transaction::getType))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
