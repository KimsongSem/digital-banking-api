package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.dtos.transaction.TransactionHistoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionHistoryMapper {
    TransactionHistoryResponse mapFromEntity(Transaction transaction);
    List<TransactionHistoryResponse> mapListFromEntities(List<Transaction> transactionList);

}

