package com.kimsong.digital_banking.services;

import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.shared.response.DataResponseDto;

public interface TransferService {
    DataResponseDto<TransferMoneyResponse> transferMoney(TransferMoneyRequest request);
}
