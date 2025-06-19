package com.kimsong.digital_banking.shared.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
public class PageConfig<T> {
    private BasePaginationHelper basePaginationHelper;
    private List<T> content;

    public static <T> PageConfig<T> getContentWithPagination(Page<T> page) {
        Objects.requireNonNull(page, "Page must not be null");

        if (page.getContent().isEmpty()) {
            return PageConfig.<T>builder()
                    .basePaginationHelper(BasePaginationHelper.builder().build())
                    .content(new ArrayList<>())
                    .build();
        }

        BasePaginationHelper paginationHelper = BasePaginationHelper.builder()
                .page(page.getNumber() + 1)
                .size(page.getContent().size())
                .totalPage(page.getTotalPages())
                .totalSize(page.getTotalElements())
                .build();

        return PageConfig.<T>builder()
                .basePaginationHelper(paginationHelper)
                .content(page.getContent())
                .build();
    }

}
