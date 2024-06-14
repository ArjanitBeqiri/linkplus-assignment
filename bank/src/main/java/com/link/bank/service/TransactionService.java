package com.link.bank.service;

import com.link.bank.model.Account;
import com.link.bank.model.Bank;
import com.link.bank.model.Transaction;
import com.link.bank.repository.BankRepository;
import com.link.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final BankRepository bankRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, BankRepository bankRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.bankRepository = bankRepository;
    }

    public Transaction processTransaction(Transaction request, boolean isFlatFee) {
        Account originatingAccount = accountService.getAccount(request.getOriginatingAccountId());
        Account resultingAccount = accountService.getAccount(request.getResultingAccountId());

        if (originatingAccount == null || resultingAccount == null) {
            throw new RuntimeException("Originating account or resulting account does not exist");
        }

        Bank bank = originatingAccount.getBank();

        double totalAmount;
        double totalFee;
        if (isFlatFee) {
            totalFee = originatingAccount.getBank().getTransactionFlatFeeAmount();
        } else {
            totalFee = request.getAmount() * originatingAccount.getBank().getTransactionPercentFeeAmount() / 100;
        }
        totalAmount = request.getAmount() + totalFee;
        if (originatingAccount.getBalance() < totalAmount) {
            throw new RuntimeException("There are no available funds for this transaction");
        }

        originatingAccount.setBalance(originatingAccount.getBalance() - totalAmount);
        resultingAccount.setBalance(resultingAccount.getBalance() + request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setOriginatingAccountId(request.getOriginatingAccountId());
        transaction.setResultingAccountId(request.getResultingAccountId());
        transaction.setTransactionReason(request.getTransactionReason());
        transactionRepository.save(transaction);

        bank.setTotalTransactionFeeAmount(bank.getTotalTransactionFeeAmount() + totalFee);
        bank.setTotalTransferAmount(bank.getTotalTransferAmount() + request.getAmount());
        bankRepository.save(bank);

        return transaction;
    }

    public List<Transaction> getTransactions(Long accountId) {
        Account account = accountService.getAccount(accountId);
        if(account == null) {
            throw new IllegalArgumentException("Account does not exist");
        }
        return transactionRepository.findByOriginatingAccountIdOrResultingAccountId(accountId, accountId);
    }

}
