package com.banking.three_di_testing.repositories;

import com.banking.three_di_testing.models.Account;
//import com.banking.three_di_testing.models.AccountTransactionDTO;
import com.banking.three_di_testing.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySourceAccountIdOrderByInitiationDate(long id);

    // Made new query to retrive filter data. Getting start date, end date and id
    // from user. id is basically account number id.
    @Query(value = "SELECT * from transaction where initiation_date > ?1 AND completion_date < ?2 AND source_account_id = ?3", nativeQuery = true)
    List<Transaction> findAllByDate(String startDate, String endDate, long id);
}
