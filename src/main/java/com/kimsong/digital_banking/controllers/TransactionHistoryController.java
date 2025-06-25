package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import com.kimsong.digital_banking.services.TransactionService;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transactions")
public class TransactionHistoryController {
    private final TransactionService transactionService;

    @GetMapping("getAllHistory")
    public ResponseEntity<PaginationResponseDto<TransactionHistoryResponse>> transactionHistory(TransactionHistoryFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllWithFilter(filter));
    }

}
