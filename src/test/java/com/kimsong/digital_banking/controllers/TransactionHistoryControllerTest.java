package com.kimsong.digital_banking.controllers;

import com.kimsong.digital_banking.constants.enums.*;
import com.kimsong.digital_banking.dtos.account.AccountResponse;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import com.kimsong.digital_banking.services.TransactionService;
import com.kimsong.digital_banking.shared.pagination.BasePaginationHelper;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionHistoryControllerTest {
    @InjectMocks
    private TransactionHistoryController transactionHistoryController;

    @Mock
    private TransactionService transactionService;

    @Test
    void getTransactionHistory_whenValidFilter_thenReturnResponseWithPagination() {
        TransactionHistoryFilter filter = new TransactionHistoryFilter();
        filter.setAccountNumber("100000001");
        filter.setPage(0);
        filter.setSize(50);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountNumber("100000001");
        accountResponse.setAccountType(EAccountType.SAVINGS);

        TransactionHistoryResponse response = new TransactionHistoryResponse();
        response.setTransactionReference("TXN20250622-172627-694");
        response.setAccount(accountResponse);
        response.setTransactionType(ETransactionType.DEBIT);
        response.setAmount(new BigDecimal("200.00"));
        response.setCurrency(ECurrency.USD);
        response.setTransactionDate(Date.from(Instant.now()));
        response.setStatus(ETransactionStatus.SUCCESS);
        response.setChannel(EChannel.API);
        response.setPurpose("Testing");
        List<TransactionHistoryResponse> content = List.of(response);

        PaginationResponseDto<TransactionHistoryResponse> paginationResponse = PaginationResponseDto.<TransactionHistoryResponse>builder()
                .pagination(BasePaginationHelper.builder().page(1).size(50).totalPage(1).totalSize(1).build())
                .data(content)
                .build();

        when(transactionService.getAll(filter)).thenReturn(paginationResponse);

        ResponseEntity<PaginationResponseDto<TransactionHistoryResponse>> result = transactionHistoryController.transactionHistory(filter);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getPagination().getTotalSize());
        assertEquals("TXN20250622-172627-694", result.getBody().getData().get(0).getTransactionReference());
        assertEquals("100000001", result.getBody().getData().get(0).getAccount().getAccountNumber());

        verify(transactionService).getAll(filter);
    }

}
