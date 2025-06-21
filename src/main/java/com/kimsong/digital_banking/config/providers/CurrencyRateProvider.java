package com.kimsong.digital_banking.config.providers;

import com.kimsong.digital_banking.utils.ECurrency;

import java.math.BigDecimal;

public interface CurrencyRateProvider {
    boolean supports(ECurrency from, ECurrency to);
    BigDecimal getRate(ECurrency from, ECurrency to);
}
