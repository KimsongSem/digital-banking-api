package com.kimsong.digital_banking.dtos.transaction;

import com.kimsong.digital_banking.dtos.account.AccountResponse;
import com.kimsong.digital_banking.utils.EChannel;
import com.kimsong.digital_banking.utils.ECurrency;
import com.kimsong.digital_banking.utils.ETransactionStatus;
import com.kimsong.digital_banking.utils.ETransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse implements Serializable {

    private Long id;

    private String transactionReference;

    private AccountResponse account;

    private ETransactionType transactionType;

    private BigDecimal amount;

    private ECurrency currency;

    private Date transactionDate;

    private ETransactionStatus status;

    private EChannel channel;

    private String purpose;

}
