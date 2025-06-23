package com.kimsong.digital_banking.exceptions;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.Getter;

@Getter
public class ResourceDuplicatedException extends RuntimeException{
    private final ErrorStatusEnum statusEnum;

    public ResourceDuplicatedException(ErrorStatusEnum statusEnum) {
        super(statusEnum.message);
        this.statusEnum = statusEnum;
    }

    public ResourceDuplicatedException(ErrorStatusEnum statusEnum, Throwable cause) {
        super(statusEnum.message, cause);
        this.statusEnum = statusEnum;
    }

}