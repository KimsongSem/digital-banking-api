package com.kimsong.digital_banking.exception;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;

public class ResourceNotFoundException extends RuntimeException{

    private final ErrorStatusEnum statusEnum;

    public ResourceNotFoundException(ErrorStatusEnum statusEnum) {
        super(statusEnum.message);
        this.statusEnum = statusEnum;
    }

    public ResourceNotFoundException(ErrorStatusEnum statusEnum, Throwable cause) {
        super(statusEnum.message, cause);
        this.statusEnum = statusEnum;
    }

    public ErrorStatusEnum getStatusEnum() {
        return statusEnum;
    }

}