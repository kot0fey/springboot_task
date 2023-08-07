package com.example.springboot_task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ApiBadRequestException.class})
    public ResponseEntity<Object> handleRequestException(ApiBadRequestException e){
        ApiException exception = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Europe/Moscow"))
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
