package com.kimsong.digital_banking.exception;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;

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

    public ErrorStatusEnum getStatusEnum() {
        return statusEnum;
    }

}