package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.services.TransferService;
import com.kimsong.digital_banking.shared.response.DataResponseDto;
import com.kimsong.digital_banking.utils.EChannel;
import com.kimsong.digital_banking.utils.ECurrency;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {
    @InjectMocks
    private TransferController transferController;

    @Mock
    private TransferService transferService;

    @Test
    void transferMoney_whenValidRequest_thenReturnResponse() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountNumber("100000001");
        request.setToAccountNumber("100000002");
        request.setAmount(new BigDecimal("100.00"));
        request.setPurpose("Testing transfer");

        TransferMoneyResponse response = new TransferMoneyResponse();
        response.setTransactionReference("TXN20250622-172627-123");
        response.setFromAccountNumber("100000001");
        response.setToAccountNumber("100000002");
        response.setFromCcy(ECurrency.USD);
        response.setToCcy(ECurrency.USD);
        response.setAmount(request.getAmount());
        response.setTransactionDate(Date.from(Instant.now()));
        response.setStatus(ETransactionStatus.SUCCESS);
        response.setChannel(EChannel.API);
        response.setPurpose(request.getPurpose());

        DataResponseDto<TransferMoneyResponse> dataResponse = DataResponseDto.<TransferMoneyResponse>builder()
                .data(response)
                .build();

        when(transferService.transferMoney(request)).thenReturn(dataResponse);

        ResponseEntity<DataResponseDto<TransferMoneyResponse>> result = transferController.transfer(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("TXN20250622-172627-123", result.getBody().getData().getTransactionReference());
        assertEquals(new BigDecimal("100.00"), result.getBody().getData().getAmount());

        verify(transferService).transferMoney(request);
    }

}