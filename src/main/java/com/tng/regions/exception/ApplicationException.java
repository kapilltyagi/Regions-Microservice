package com.tng.regions.exception;
public class ApplicationException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public ApplicationException(String message) {
        super(message);
    }
}
