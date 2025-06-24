package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import com.kimsong.digital_banking.services.AccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<DataResponseDto<CustomerAccountResponse>> createCustomerAccount(@Valid @RequestBody CreateCustomerAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request));
    }

    @GetMapping("{accountNumber}/balance")
    public ResponseEntity<DataResponseDto<CheckAccountBalanceResponse>> balance(@PathVariable String accountNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.checkAccountBalance(accountNumber));
    }

}
