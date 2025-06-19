package com.kimsong.digital_banking.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimsong.digital_banking.models.Account;
import com.kimsong.digital_banking.utils.EChannel;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import com.kimsong.digital_banking.utils.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionHistoryResponse {

    private Long id;

    private AccountResponse account;

    private ETransactionType transactionType;

    private BigDecimal amount;

    private String currency;

    private Date transactionDate;

    private ETransactionStatus status;

    private String referenceNumber;

    private EChannel channel;

    private String purpose;

}
