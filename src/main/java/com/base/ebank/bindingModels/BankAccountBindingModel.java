package com.base.ebank.bindingModels;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BankAccountBindingModel{

    private Long id;
    private String username;
    private String iban;
    private BigDecimal amount;
    private Long receiverId;

    public BankAccountBindingModel() {
    }

    @NotEmpty
    @NotNull
    public Long getId() {
        return this.id;
    }
    @NotEmpty
    @NotNull
    public void setId(Long id) {
        this.id = id;
    }
    @NotEmpty
    @NotNull
    public String getUsername() {
        return this.username;
    }
    @NotEmpty
    @NotNull
    public void setUsername(String username) {
        this.username = username;
    }
    @NotEmpty
    @NotNull
    public String getIban() {
        return this.iban;
    }
    @NotEmpty
    @NotNull
    public void setIban(String iban) {
        this.iban = iban;
    }
    @NotEmpty
    @NotNull
    public BigDecimal getAmount() {
        return this.amount;
    }
    @NotEmpty
    @NotNull
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    @NotEmpty
    @NotNull
    public Long getReceiverId() {
        return this.receiverId;
    }
    @NotEmpty
    @NotNull
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }






}
