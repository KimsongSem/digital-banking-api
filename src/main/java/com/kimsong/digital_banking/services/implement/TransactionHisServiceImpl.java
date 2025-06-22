package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryFilter;
import com.kimsong.digital_banking.mapper.TransactionHistoryMapper;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import com.kimsong.digital_banking.repositories.TransactionRepository;
import com.kimsong.digital_banking.services.TransactionService;
import com.kimsong.digital_banking.shared.pagination.PageConfig;
import com.kimsong.digital_banking.shared.pagination.PaginationConfig;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
import com.kimsong.digital_banking.specifications.TransactionSpecification;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import com.kimsong.digital_banking.utils.ETransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionHisServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionHistoryMapper transactionHistoryMapper;
    private final PaginationConfig paginationConfig;

    @Override
    public void createTransaction(Transaction transaction, ETransactionType transactionType) {
        transactionRepository.save(transaction);
        log.info("{} transaction created with ref: {}", transactionType, transaction.getTransactionReference());
    }

    @Override
    public BigDecimal getTodayDebitTotal(Account account) {
        Date startDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Transaction> transactions = transactionRepository.findByAccountAndTransactionTypeAndStatusAndTransactionDateBetween(
                account,
                ETransactionType.DEBIT,
                ETransactionStatus.SUCCESS,
                startDate,
                endDate
        );

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public PaginationResponseDto<TransactionHistoryResponse> getAll(TransactionHistoryFilter filter) {
        PageRequest pageRequest = PageRequest.of(
                filter.getPage() <= 0 ? paginationConfig.getPage() - 1 : filter.getPage() - 1,
                filter.getSize() <= 0 ? paginationConfig.getSize() : filter.getSize());

        Page<Transaction> transactionPage = transactionRepository.findAll(
                TransactionSpecification.getSpecification(filter),
                pageRequest);
        PageConfig<Transaction> pageConfig = PageConfig.getContentWithPagination(transactionPage);

        return PaginationResponseDto.<TransactionHistoryResponse>builder()
                .pagination(pageConfig.getBasePaginationHelper())
                .data(transactionHistoryMapper.mapListFromEntities(pageConfig.getContent()))
                .build();

    }

}
