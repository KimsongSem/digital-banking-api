package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.payloads.request.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.response.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.shared.response.DataResponseDto;

public interface IAccountService {
    void createAccount(CreateCustomerAccountRequest request);

    DataResponseDto<CheckAccountBalanceResponse> checkAccountBalance(CheckAccountBalanceRequest request);

}
