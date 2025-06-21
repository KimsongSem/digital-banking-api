package com.kimsong.digital_banking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "amount.usd.limit")
@Component
@Data
public class LimitTransferProperties {
    private BigDecimal singleTransfer;
    private BigDecimal dailyTransfer;
}