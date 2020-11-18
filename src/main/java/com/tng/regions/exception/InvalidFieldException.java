package com.tng.regions.exception;

import org.springframework.http.HttpStatus;

public class InvalidFieldException extends RuntimeException{
    private static final long SerialVersionUID = 1l;
    private String message;
    private HttpStatus httpStatus;

    public InvalidFieldException(String message){
        this.setMessage(message);
    }

    public InvalidFieldException(String message,HttpStatus httpStatus){
        this.message =message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
