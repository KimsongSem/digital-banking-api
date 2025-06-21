package com.kimsong.digital_banking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "currency.rates")
@Component
@Data
public class CurrencyProperties {
//    private Map<String, BigDecimal> rates = new HashMap<>();
    private BigDecimal USD_KHR;
    private BigDecimal KHR_USD;
}