package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.shared.response.DataResponseDto;

public interface AccountService {
    DataResponseDto<CustomerAccountResponse> createAccount(CreateCustomerAccountRequest request);

    DataResponseDto<CheckAccountBalanceResponse> checkAccountBalance(String accountNumber);

    Account getAccountWithLockByAccountNumber(String accountNumber, boolean isFromAccount);

    void updateAccount(Account account);
}
