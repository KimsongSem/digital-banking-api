package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.dtos.account.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.dtos.account.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.dtos.account.CustomerAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferMoneyResponse mapFromRequest(TransferMoneyRequest request);

}

