package com.banking.three_di_testing.services;

import com.banking.three_di_testing.models.Account;
import com.banking.three_di_testing.models.Transaction;
import com.banking.three_di_testing.repositories.AccountRepository;
import com.banking.three_di_testing.repositories.TransactionRepository;
import com.banking.three_di_testing.utils.CodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account getAccount(String sortCode, String accountNumber) {
        Optional<Account> account = accountRepository
                .findBySortCodeAndAccountNumber(sortCode, accountNumber);

        account.ifPresent(value -> value.setTransactions(transactionRepository
                .findBySourceAccountIdOrderByInitiationDate(value.getId())));

        return account.orElse(null);
    }

    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);

        return account.orElse(null);
    }

    public Account createAccount(String bankName, String ownerName) {
        CodeGenerator codeGenerator = new CodeGenerator();
        Account newAccount = new Account(bankName, ownerName, codeGenerator.generateSortCode(),
                codeGenerator.generateAccountNumber(), 0.00);
        return accountRepository.save(newAccount);
    }

    public Account findTransactionsBySourceCodeAndAccountNumber(String accountNumber, String sortCode, String startDate,
            String endDate) {
        Optional<Account> account = accountRepository
                .findBySortCodeAndAccountNumber(sortCode, accountNumber);

        System.out.println("Account List: " + account);

        account.ifPresent(value ->

        // made new method FindAllByDate in repository and called here.
        {
            System.out.println("Value: " + value);
            value.setTransactions(transactionRepository
                    .findAllByDate(startDate, endDate, value.getId()));
        });

        return account.orElse(null);
    }
}
