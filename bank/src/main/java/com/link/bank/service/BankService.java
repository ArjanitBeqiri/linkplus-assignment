package com.link.bank.service;

import com.link.bank.model.Bank;
import com.link.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank getBankById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank createBank(String bankName, double transactionFeeAmount, double flatFeeAmount){
        Bank newBank = new Bank(bankName, transactionFeeAmount, flatFeeAmount);
        return bankRepository.save(newBank);
    }
    public double getTotalTransactionFee(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new IllegalArgumentException("Bank not found"));
        return bank.getTotalTransactionFeeAmount();
    }

    public double getTotalTransferAmount(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new IllegalArgumentException("Bank not found"));
        return bank.getTotalTransferAmount();
    }
}

