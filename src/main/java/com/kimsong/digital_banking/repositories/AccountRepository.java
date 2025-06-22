package com.kimsong.digital_banking.repositories;

import com.kimsong.digital_banking.models.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockByAccountNumber(String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

}