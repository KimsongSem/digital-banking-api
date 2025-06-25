package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import com.kimsong.digital_banking.mappers.TransactionHistoryMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.repositories.TransactionRepository;
import com.kimsong.digital_banking.services.implement.TransactionHisServiceImpl;
import com.kimsong.digital_banking.shared.pagination.PaginationConfig;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
import com.kimsong.digital_banking.constants.enums.EChannel;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import com.kimsong.digital_banking.constants.enums.ETransactionStatus;
import com.kimsong.digital_banking.constants.enums.ETransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionHisServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionHistoryMapper transactionHistoryMapper;

    @Mock
    private PaginationConfig paginationConfig;

    @InjectMocks
    private TransactionHisServiceImpl transactionService;

    @Test
    void createTransaction_whenCalled_thenSaveTransaction() {
        Account account = new Account();
        account.setAccountNumber("100000001");

        Transaction transaction = new Transaction();
        transaction.setTransactionReference("TXN20250622-172627-111");
        transaction.setAccount(account);
        transaction.setTransactionType(ETransactionType.DEBIT);
        transaction.setAmount(new BigDecimal("200.00"));
        transaction.setCurrency(ECurrency.USD);
        transaction.setTransactionDate(Date.from(Instant.now()));
        transaction.setStatus(ETransactionStatus.SUCCESS);
        transaction.setChannel(EChannel.API);
        transaction.setPurpose("Testing");

        transactionService.createTransaction(transaction, ETransactionType.DEBIT);

        verify(transactionRepository).save(transaction);
    }

    @Test
    void getTodayDebitTotal_whenTransactionsFound_thenReturnSum() {
        Account account = new Account();
        account.setAccountNumber("100000001");

        Transaction transaction_1 = new Transaction();
        transaction_1.setTransactionReference("TXN20250622-172627-111");
        transaction_1.setAccount(account);
        transaction_1.setTransactionType(ETransactionType.DEBIT);
        transaction_1.setAmount(new BigDecimal("100.00"));
        transaction_1.setCurrency(ECurrency.USD);

        Transaction transaction_2 = new Transaction();
        transaction_2.setTransactionReference("TXN20250622-172627-112");
        transaction_2.setAccount(account);
        transaction_2.setTransactionType(ETransactionType.DEBIT);
        transaction_2.setAmount(new BigDecimal("200.00"));
        transaction_2.setCurrency(ECurrency.USD);

        List<Transaction> transactions = List.of(transaction_1, transaction_2);

        when(transactionRepository.findByAccountAndTransactionTypeAndStatusAndTransactionDateBetween(
                eq(account),
                eq(ETransactionType.DEBIT),
                eq(ETransactionStatus.SUCCESS),
                any(Date.class),
                any(Date.class)
        )).thenReturn(transactions);

        BigDecimal result = transactionService.getTodayDebitTotal(account);

        assertEquals(new BigDecimal("300.00"), result);
    }

    @Test
    void getAllTransactionHistory_whenValidFilter_thenReturnResponseWithPagination() {
        TransactionHistoryFilter filter = new TransactionHistoryFilter();
        filter.setAccountNumber("100000001");
        filter.setPage(0);
        filter.setSize(10);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Transaction> transactionPage = new PageImpl<>(List.of(new Transaction()));

        lenient().when(paginationConfig.getPage()).thenReturn(1);
        lenient().when(paginationConfig.getSize()).thenReturn(10);
        when(transactionRepository.findAll(any(Specification.class), eq(pageRequest)))
                .thenReturn(transactionPage);
        when(transactionHistoryMapper.mapListFromEntities(anyList()))
                .thenReturn(List.of(new TransactionHistoryResponse()));

        PaginationResponseDto<TransactionHistoryResponse> result = transactionService.getAllWithFilter(filter);

        assertNotNull(result);
        assertNotNull(result.getData());
        assertEquals(1, result.getData().size());

        verify(transactionRepository).findAll(any(Specification.class), eq(pageRequest));
        verify(transactionHistoryMapper).mapListFromEntities(anyList());
    }

}
