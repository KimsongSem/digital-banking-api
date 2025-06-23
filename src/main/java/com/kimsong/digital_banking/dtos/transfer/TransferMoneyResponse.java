package com.kimsong.digital_banking.dtos.transfer;

import com.kimsong.digital_banking.constants.enums.EChannel;
import com.kimsong.digital_banking.constants.enums.ECurrency;
import com.kimsong.digital_banking.constants.enums.ETransactionStatus;
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
public class TransferMoneyResponse implements Serializable {

    private String transactionReference;

    private String fromAccountNumber;

    private ECurrency fromCcy;

    private String toAccountNumber;

    private ECurrency toCcy;

    private BigDecimal amount;

    private Date transactionDate;

    private ETransactionStatus status;

    private EChannel channel;

    private String purpose;

}
