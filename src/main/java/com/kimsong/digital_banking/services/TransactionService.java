package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;

import java.math.BigDecimal;

public interface TransactionService {
    void createTransaction(Transaction transaction);

    BigDecimal getTodayDebitTotal(Account account);

    PaginationResponseDto<TransactionHistoryResponse> getAll(TransactionHistoryFilter filter);

}
