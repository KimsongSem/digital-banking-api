package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.services.TransferService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
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
@RequestMapping("/api/transfer")
public class TransferController {
    private final TransferService transferService;

    @PostMapping()
    public ResponseEntity<DataResponseDto<TransferMoneyResponse>> transfer(@Valid @RequestBody TransferMoneyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(transferService.transferMoney(request));
    }

}