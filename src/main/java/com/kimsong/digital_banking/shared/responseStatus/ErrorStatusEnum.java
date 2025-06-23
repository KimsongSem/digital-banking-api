package com.kimsong.digital_banking.shared.responseStatus;

public enum ErrorStatusEnum {
    SUCCESS(200, "Success"),

    BAD_REQUEST(400, "Missing mandatory field"),
    CUSTOMER_ALREADY_EXISTS(400, "Customer already exists"),
    UNSUPPORTED_CURRENCY_PAIR(400, "Unsupported currency pair"),
    SAME_ACCOUNT_TRANSFER(400, "Can't transfer same account"),
    SINGLE_TRANSFER_EXCEEDED_LIMIT(400, "Single transfer exceeded limit"),
    DAILY_TRANSFER_EXCEEDED_LIMIT(400, "Daily transfer exceeded limit"),
    AMOUNT_LESS_THAN_MIN_LIMIT(400, "Amount is less than minimum limit"),
    INSUFFICIENT_BALANCE(400, "Insufficient balance"),
    ACCOUNT_NOT_FOUND(404, "Account not found"),
    FROM_ACCOUNT_NOT_FOUND(404, "From account not found"),
    TO_ACCOUNT_NOT_FOUND(404, "To account not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    public final int status;
    public final String message;

    ErrorStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

}