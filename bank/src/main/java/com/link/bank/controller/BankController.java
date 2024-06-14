package com.link.bank.controller;

import com.link.bank.model.Bank;
import com.link.bank.service.BankService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{bankId}")
    public Bank getBankById(@PathVariable Long bankId) {
        return bankService.getBankById(bankId);
    }

    @GetMapping
    public List<Bank> getAllBanks() {
        return bankService.getAllBanks();
    }

    @PostMapping
    public Bank createBank(@RequestParam String name, @RequestParam double transactionFlatFeeAmount, @RequestParam double transactionPercentFeeAmount) {
        return bankService.createBank(name, transactionPercentFeeAmount, transactionFlatFeeAmount);
    }

    @GetMapping("/{bankId}/totalTransactionFee")
    public double getTotalTransactionFee(@PathVariable Long bankId) {
        return bankService.getTotalTransactionFee(bankId);
    }

    @GetMapping("/{bankId}/getTotalTransferAmount")
    public double getTotalTransferAmount(@PathVariable Long bankId) {
        return bankService.getTotalTransferAmount(bankId);
    }
}