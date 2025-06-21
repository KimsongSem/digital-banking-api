package com.kimsong.digital_banking.dtos.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyResponse implements Serializable {

    private Integer fromAccountNumber;

    private Integer toAccountNumber;

    private BigDecimal amount;

    private String purpose;

}
