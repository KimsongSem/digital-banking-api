package com.kimsong.digital_banking.exceptions.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude()
public class ErrorResponseDetail<E> extends ErrorResponse {
    private E data;

}