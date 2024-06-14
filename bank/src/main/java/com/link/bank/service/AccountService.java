package com.link.bank.service;

import com.link.bank.model.Account;
import com.link.bank.model.Bank;
import com.link.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankService bankService;

    public AccountService(AccountRepository accountRepository, BankService bankService) {
        this.accountRepository = accountRepository;
        this.bankService = bankService;
    }

    public Account createAccount(Long bankId, String username, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be below zero");
        }

        Bank bank = bankService.getBankById(bankId);

        if (bank != null) {
            Account newAccount = new Account(username, balance);
            bank.getAccounts().add(newAccount);
            return accountRepository.save(newAccount);
        } else {
            throw new RuntimeException("Account could not be created");
        }
    }

    public Account getAccount(Long id) {
        return accountRepository.findAccountByAccountId(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public String withdraw(double amount, Long accountId){
        if (amount < 0) {
            throw new IllegalArgumentException("Withdraw amount cannot be below zero");
        }

        Account account = getAccount(accountId);
        if(account == null) {
            throw new RuntimeException("Account not found");
        }

        if(account.getBalance() < amount) {
            throw new RuntimeException("There are no available funds for this transaction");
        }

        account.withdraw(amount);
        accountRepository.save(account);

        return amount + " amount withdraw successfully";
    }

    public String deposit(double amount, Long accountId){
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount cannot be below zero");
        }

        Account account = getAccount(accountId);
        if(account == null) {
            throw new RuntimeException("Account not found");
        }

        account.deposit(amount);
        accountRepository.save(account);

        return amount + " amount deposited successfully";
    }

    public double getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }
}