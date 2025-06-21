package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.shared.response.DataResponseDto;

public interface AccountService {
    void createAccount(CreateCustomerAccountRequest request);

    DataResponseDto<CheckAccountBalanceResponse> checkAccountBalance(CheckAccountBalanceRequest request);

    Account getAccountByAccountNumber(Integer accountNumber, boolean isFromAccount);

    void updateAccount(Account account);
}
