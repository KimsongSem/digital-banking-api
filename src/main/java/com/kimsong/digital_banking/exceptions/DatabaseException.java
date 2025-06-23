package com.kimsong.digital_banking.exceptions;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.Getter;

@Getter
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

}
