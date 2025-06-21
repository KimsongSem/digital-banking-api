package com.kimsong.digital_banking.exception.response;

import com.kimsong.digital_banking.shared.responseStatus.ErrorStatusEnum;
import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(ErrorStatusEnum commonStatusEnum) {
        this.status = commonStatusEnum.status;
        this.message = commonStatusEnum.message;
    }

    public ErrorResponse() {}

}