package com.kimsong.digital_banking.dtos.account;

import com.kimsong.digital_banking.utils.ECurrency;
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
public class CheckAccountBalanceResponse implements Serializable {

    private String accountNumber;

    private BigDecimal balance;

    private ECurrency currency;

}
