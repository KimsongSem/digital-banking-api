package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.response.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<ResponseDTO> createCustomerAccount(@Valid @RequestBody CreateCustomerAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.create());
    }

    @PostMapping("checkBalance")
    public ResponseEntity<DataResponseDto<CheckAccountBalanceResponse>> balance(@RequestBody CheckAccountBalanceRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.checkAccountBalance(request));
    }

}
