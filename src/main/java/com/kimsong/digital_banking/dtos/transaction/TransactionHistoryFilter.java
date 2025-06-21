package com.kimsong.digital_banking.dtos.transaction;

import com.kimsong.digital_banking.shared.filter.BaseFilterHelper;
import lombok.Data;

@Data
public class TransactionHistoryFilter extends BaseFilterHelper {
    private Integer accountNumber;
    private String transactionReference;
}
