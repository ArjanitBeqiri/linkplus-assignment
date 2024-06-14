package com.link.bank.controller;

import com.link.bank.model.Transaction;
import com.link.bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactionsOfAnAccount(@PathVariable Long accountId) {
        return transactionService.getTransactions(accountId);
    }

    @PostMapping("/processTransaction")
    public Transaction processTransaction(@RequestBody Transaction request, @RequestParam boolean isFlatFee) {
        return transactionService.processTransaction(request, isFlatFee);
    }
}