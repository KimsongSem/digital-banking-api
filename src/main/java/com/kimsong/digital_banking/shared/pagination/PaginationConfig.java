package com.kimsong.digital_banking.shared.pagination;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pagination.default")
@Component
@Data
public class PaginationConfig {
    private Integer page;
    private Integer size;

}