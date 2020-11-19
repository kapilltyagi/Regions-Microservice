package com.tng.regions.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception){
        ApiException apiException = new ApiException(exception.getMessage(),exception, HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidFieldException.class})
    public ResponseEntity<Object> handleInvalidFieldException(InvalidFieldException exception){
        InvalidFieldException apiException = new InvalidFieldException(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY);
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception){
        InvalidFieldException apiException = new InvalidFieldException(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException exception){
        InvalidFieldException apiException = new InvalidFieldException(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception exception){
        InvalidFieldException apiException = new InvalidFieldException(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
}
