package com.kimsong.digital_banking.dtos.account;

import com.kimsong.digital_banking.dtos.customer.CustomerResponse;
import com.kimsong.digital_banking.constants.enums.EAccountStatus;
import com.kimsong.digital_banking.constants.enums.EAccountType;
import com.kimsong.digital_banking.constants.enums.ECurrency;
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
public class CustomerAccountResponse implements Serializable {
    private Long id;

    private CustomerResponse customer;

    private String accountNumber;

    private EAccountType accountType;

    private BigDecimal balance;

    private ECurrency currency;

    private EAccountStatus status;

    private Date openedAt;

}
