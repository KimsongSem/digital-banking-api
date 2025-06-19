package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.payloads.response.TransactionHistoryResponse;
import com.kimsong.digital_banking.shared.response.PaginationResponseDto;

public interface ITransactionHisService {
    PaginationResponseDto<TransactionHistoryResponse> getAll();

}
