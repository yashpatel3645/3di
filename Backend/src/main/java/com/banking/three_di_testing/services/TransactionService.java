package com.banking.three_di_testing.services;

import com.banking.three_di_testing.constants.ACTION;
import com.banking.three_di_testing.models.Account;
//import com.banking.three_di_testing.models.AccountTransactionDTO;
import com.banking.three_di_testing.models.Transaction;
import com.banking.three_di_testing.repositories.AccountRepository;
import com.banking.three_di_testing.repositories.TransactionRepository;
import com.banking.three_di_testing.utils.TransactionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean makeTransfer(TransactionInput transactionInput) {
          String sourceSortCode = transactionInput.getSourceAccount().getSortCode();
        String sourceAccountNumber = transactionInput.getSourceAccount().getAccountNumber();
        Optional<Account> sourceAccount = accountRepository
                .findBySortCodeAndAccountNumber(sourceSortCode, sourceAccountNumber);

        String targetSortCode = transactionInput.getTargetAccount().getSortCode();
        String targetAccountNumber = transactionInput.getTargetAccount().getAccountNumber();
        Optional<Account> targetAccount = accountRepository
                .findBySortCodeAndAccountNumber(targetSortCode, targetAccountNumber);

        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (isAmountAvailable(transactionInput.getAmount(), sourceAccount.get().getCurrentBalance())) {
                Transaction transaction = new Transaction();

                transaction.setAmount(transactionInput.getAmount());
                transaction.setSourceAccountId(sourceAccount.get().getId());
                transaction.setTargetAccountId(targetAccount.get().getId());
                transaction.setTargetOwnerName(targetAccount.get().getOwnerName());
                transaction.setInitiationDate(LocalDateTime.now());
                transaction.setCompletionDate(LocalDateTime.now());
                transaction.setReference(transactionInput.getReference());
//                transaction.setLatitude(transactionInput.getLatitude());
//                transaction.setLongitude(transactionInput.getLongitude());

                updateAccountBalance(sourceAccount.get(), transactionInput.getAmount(), ACTION.WITHDRAW);
                transactionRepository.save(transaction);

                return true;
            }
        }
        return false;
    }

     public void updateAccountBalance(Account account, double amount, ACTION action) {
        if (action == ACTION.WITHDRAW) {
            account.setCurrentBalance((account.getCurrentBalance() - amount));
        } else if (action == ACTION.DEPOSIT) {
            account.setCurrentBalance((account.getCurrentBalance() + amount));
        }
        accountRepository.save(account);
    }

    public boolean isAmountAvailable(double amount, double accountBalance) {
        return (accountBalance - amount) > 0;
    }



}
