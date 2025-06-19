package com.kimsong.digital_banking.services.implement;

import com.kimsong.digital_banking.mapper.ITransactionHistoryMapper;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.payloads.response.TransactionHistoryResponse;
import com.kimsong.digital_banking.repositories.TransactionRepository;
import com.kimsong.digital_banking.services.ITransactionHisService;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionHisServiceImpl implements ITransactionHisService {

    private final TransactionRepository transactionRepository;
    private final ITransactionHistoryMapper transactionHistoryMapper;

    @Override
    public PaginationResponseDto<TransactionHistoryResponse> getAll() {
        List<Transaction> getAllTxnHistory = transactionRepository.findAll();

        return PaginationResponseDto.<TransactionHistoryResponse>builder()
                .pagination(null)
                .data(transactionHistoryMapper.mapListFromEntities(getAllTxnHistory))
                .build();
    }
}
