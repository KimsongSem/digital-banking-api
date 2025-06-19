package com.kimsong.digital_banking.mapper;

import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.models.Customer;
import com.kimsong.digital_banking.models.Transaction;
import com.kimsong.digital_banking.payloads.request.CreateCustomerAccountRequest;
import com.kimsong.digital_banking.payloads.response.CheckAccountBalanceResponse;
import com.kimsong.digital_banking.payloads.response.CustomerAccountResponse;
import com.kimsong.digital_banking.payloads.response.TransactionHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface ITransactionHistoryMapper {
    TransactionHistoryResponse mapFromEntity(Transaction transaction);
    List<TransactionHistoryResponse> mapListFromEntities(List<Transaction> transactionList);

}

