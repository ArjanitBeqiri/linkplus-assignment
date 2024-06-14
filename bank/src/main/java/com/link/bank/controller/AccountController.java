package com.link.bank.controller;

import com.link.bank.model.Account;
import com.link.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public Account getAccountById(@PathVariable Long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping("{bankId}")
    public Account createAccount(@PathVariable Long bankId, @RequestParam String userName, @RequestParam double balance) {
        return accountService.createAccount(bankId, userName, balance);
    }

    @PostMapping("/{accountId}/withdraw/{amount}")
    public String withdraw(@PathVariable Long accountId, @PathVariable double amount) {
        return accountService.withdraw(amount, accountId);
    }

    @PostMapping("/{accountId}/deposit/{amount}")
    public String deposit( @PathVariable Long accountId, @PathVariable double amount) {
        return accountService.deposit(amount, accountId);
    }

    @GetMapping("/{accountId}/balance")
    public double getBalance(@PathVariable Long accountId) {
        return accountService.getBalance(accountId);
    }
}
