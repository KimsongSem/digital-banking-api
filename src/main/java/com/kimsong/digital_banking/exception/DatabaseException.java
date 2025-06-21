package com.kimsong.digital_banking.exception;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;

public class DatabaseException extends RuntimeException{
    private final ErrorStatusEnum statusEnum;

    public DatabaseException(ErrorStatusEnum statusEnum) {
        super(statusEnum.message);
        this.statusEnum = statusEnum;
    }

    public DatabaseException(ErrorStatusEnum statusEnum, Throwable cause) {
        super(statusEnum.message, cause);
        this.statusEnum = statusEnum;
    }

    public ErrorStatusEnum getStatusEnum() {
        return statusEnum;
    }
}
