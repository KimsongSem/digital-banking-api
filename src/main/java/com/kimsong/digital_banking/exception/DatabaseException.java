package com.kimsong.digital_banking.exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message){
        super(message);
    }
}
