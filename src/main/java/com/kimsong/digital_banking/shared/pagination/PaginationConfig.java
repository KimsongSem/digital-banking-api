package com.kimsong.digital_banking.shared.pagination;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class PaginationConfig {

    @Value("${pagination.default.page}")
    private int page;

    @Value("${pagination.default.size}")
    private int size;
}
