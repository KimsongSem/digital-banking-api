package com.kimsong.digital_banking.dtos.account;

import com.kimsong.digital_banking.dtos.customer.CustomerResponse;
import com.kimsong.digital_banking.constants.enums.EAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse implements Serializable {

    private String accountNumber;

    private EAccountType accountType;

    private CustomerResponse customer;

}
