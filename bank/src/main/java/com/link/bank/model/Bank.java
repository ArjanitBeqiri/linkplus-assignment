package com.link.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bankId;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    @JsonIgnoreProperties("bank")
    private List<Account> accounts;
    private double totalTransactionFeeAmount;
    private double totalTransferAmount;
    private double transactionFlatFeeAmount;
    private double transactionPercentFeeAmount;

    public Bank() {
    }

    public Bank(String name, double transactionPercentFeeAmount, double transactionFlatFeeAmount) {
        this.name = name;
        this.transactionPercentFeeAmount = transactionPercentFeeAmount;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
    }

    public Bank(String name, List<Account> accounts, double totalTransactionFeeAmount, double totalTransferAmount, double transactionFlatFeeAmount, double transactionPercentFeeAmount) {
        this.name = name;
        this.accounts = accounts;
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
        this.totalTransferAmount = totalTransferAmount;
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
        this.transactionPercentFeeAmount = transactionPercentFeeAmount;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public double getTotalTransactionFeeAmount() {
        return totalTransactionFeeAmount;
    }

    public void setTotalTransactionFeeAmount(double totalTransactionFeeAmount) {
        this.totalTransactionFeeAmount = totalTransactionFeeAmount;
    }

    public double getTotalTransferAmount() {
        return totalTransferAmount;
    }

    public void setTotalTransferAmount(double totalTransferAmount) {
        this.totalTransferAmount = totalTransferAmount;
    }

    public double getTransactionFlatFeeAmount() {
        return transactionFlatFeeAmount;
    }

    public void setTransactionFlatFeeAmount(double transactionFlatFeeAmount) {
        this.transactionFlatFeeAmount = transactionFlatFeeAmount;
    }

    public double getTransactionPercentFeeAmount() {
        return transactionPercentFeeAmount;
    }

    public void setTransactionPercentFeeAmount(double transactionPercentFeeAmount) {
        this.transactionPercentFeeAmount = transactionPercentFeeAmount;
    }
}
