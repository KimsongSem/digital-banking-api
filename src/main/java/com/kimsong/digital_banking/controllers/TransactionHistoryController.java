package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.payloads.response.TransactionHistoryResponse;
import com.kimsong.digital_banking.services.ITransactionHisService;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transactionHistory")
public class TransactionHistoryController {
    private final ITransactionHisService transactionHisService;

    @GetMapping("getAll")
    public ResponseEntity<PaginationResponseDto<TransactionHistoryResponse>> transactionHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionHisService.getAll());
    }

}
