package com.kimsong.digital_banking.shared.response;

import com.kimsong.digital_banking.shared.pagination.BasePaginationHelper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class PaginationResponseDto<T> extends ResponseDTO {
    BasePaginationHelper pagination;
    List<T> data;
}
