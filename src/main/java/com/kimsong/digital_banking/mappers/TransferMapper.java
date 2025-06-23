package com.kimsong.digital_banking.mappers;

import com.kimsong.digital_banking.dtos.transfer.TransferMoneyRequest;
import com.kimsong.digital_banking.dtos.transfer.TransferMoneyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    TransferMoneyResponse mapFromRequest(TransferMoneyRequest request);

}

