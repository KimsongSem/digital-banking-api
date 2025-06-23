package com.kimsong.digital_banking.exceptions;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{
    private final ErrorStatusEnum statusEnum;

    public ValidationException(ErrorStatusEnum statusEnum) {
        super(statusEnum.message);
        this.statusEnum = statusEnum;
    }

    public ValidationException(ErrorStatusEnum statusEnum, Throwable cause) {
        super(statusEnum.message, cause);
        this.statusEnum = statusEnum;
    }

}