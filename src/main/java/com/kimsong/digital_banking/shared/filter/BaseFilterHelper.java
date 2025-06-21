package com.kimsong.digital_banking.shared.filter;

import lombok.Data;

@Data
public class BaseFilterHelper {
    private String search;
    private int page;
    private int size;
}
