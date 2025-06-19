package com.kimsong.digital_banking.shared.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BasePaginationHelper {
    int page;
    int size;
    int totalPage;
    long totalSize;
}
