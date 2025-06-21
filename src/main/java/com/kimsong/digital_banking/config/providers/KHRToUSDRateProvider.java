package com.kimsong.digital_banking.config.providers;

import com.kimsong.digital_banking.config.CurrencyProperties;
import com.kimsong.digital_banking.utils.ECurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class KHRToUSDRateProvider implements CurrencyRateProvider {

    private final CurrencyProperties currencyProp;
    @Override
    public boolean supports(ECurrency from, ECurrency to) {
        return from.equals(ECurrency.KHR) && to.equals(ECurrency.USD);
    }

    @Override
    public BigDecimal getRate(ECurrency from, ECurrency to) {
        return BigDecimal.ONE.divide(currencyProp.getKHR_USD(), 8, RoundingMode.HALF_UP);
    }
}
