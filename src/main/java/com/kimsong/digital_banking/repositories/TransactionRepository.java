package com.kimsong.digital_banking.repositories;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.constants.enums.ETransactionStatus;
import com.kimsong.digital_banking.constants.enums.ETransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountAndTransactionTypeAndStatusAndTransactionDateBetween(
            Account account,
            ETransactionType transactionType,
            ETransactionStatus status,
            Date start,
            Date end
    );

    Page<Transaction> findAll(Specification<Transaction> specification, Pageable pageable);

}