package com.base.ebank.repositories;

import com.base.ebank.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface   TransactionRepository extends JpaRepository<Transaction,Long> {
}
