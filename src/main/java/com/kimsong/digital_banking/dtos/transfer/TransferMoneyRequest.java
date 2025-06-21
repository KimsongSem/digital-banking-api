package com.kimsong.digital_banking.dtos.transfer;

import com.kimsong.digital_banking.utils.ECurrency;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {
    @NotNull
    private Integer fromAccountNumber;

    @NotNull
    private Integer toAccountNumber;

    @NotNull
//    @DecimalMin(value = "0.01", message = "Amount is less than minimum limit")
//    @DecimalMax(value = "10000.00", message = "Amount transfer is exceeds limit")
    private BigDecimal amount;

    private String purpose;
}