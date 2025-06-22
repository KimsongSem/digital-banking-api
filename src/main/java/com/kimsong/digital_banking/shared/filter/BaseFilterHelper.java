package com.kimsong.digital_banking.shared.filter;

import lombok.Data;

@Data
public class BaseFilterHelper {
    private String keyword;
    private int page;
    private int size;
}
