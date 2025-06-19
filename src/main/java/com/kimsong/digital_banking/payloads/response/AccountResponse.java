package com.kimsong.digital_banking.payloads.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kimsong.digital_banking.utils.EAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountResponse {
    private Long id;
//    private Customer customer;

    private String accountNumber;

    private EAccountType accountType;

}
