package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.payloads.request.CheckAccountBalanceRequest;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.response.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.services.IAccountService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.shared.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/account")
public class AccountController {
    private final IAccountService accountService;

    @PostMapping("createCustomerAccount")
    public ResponseEntity<ResponseDTO> createCustomerAccount(@RequestBody CreateCustomerAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.create());
    }

    @PostMapping("transfer")
    public ResponseEntity<ResponseDTO> transfer() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.success());
    }

    @PostMapping("balance")
    public ResponseEntity<DataResponseDto<CheckAccountBalanceResponse>> balance(@RequestBody CheckAccountBalanceRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.checkAccountBalance(request));
    }

}
