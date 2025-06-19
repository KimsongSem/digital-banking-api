package com.kimsong.digital_banking.shared.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class DataResponseDto<T> extends ResponseDTO {
    private T data;
}
