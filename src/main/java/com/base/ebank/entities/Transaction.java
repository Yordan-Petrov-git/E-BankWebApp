package com.base.ebank.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction {
    private Long id;
    private String type;
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private BigDecimal amount;

    public Transaction() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "transaction_type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "sender", updatable = false)
    public BankAccount getFromAccount() {
        return this.fromAccount;
    }

    public void setFromAccount(BankAccount fromAccount) {
        this.fromAccount = fromAccount;
    }

    @ManyToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "reciver", updatable = false)
    public BankAccount getToAccount() {
        return this.toAccount;
    }

    public void setToAccount(BankAccount toAccount) {
        this.toAccount = toAccount;
    }

    @Column(name = "amount", nullable = false, updatable = false)
    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
