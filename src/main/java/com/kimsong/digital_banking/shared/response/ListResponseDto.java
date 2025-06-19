package com.kimsong.digital_banking.shared.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ListResponseDto<T> extends ResponseDTO {
    private List<T> data;
}
