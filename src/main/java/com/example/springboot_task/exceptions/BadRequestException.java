package com.example.springboot_task.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    //controller advise in base package
    /*
    @ExceptionHandler({ ConstraintViolationException.class })
public ResponseEntity<Object> handleConstraintViolation(
  ConstraintViolationException ex, WebRequest request) {
    List<String> errors = new ArrayList<String>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
        errors.add(violation.getRootBeanClass().getName() + " " +
          violation.getPropertyPath() + ": " + violation.getMessage());
    }

    ApiError apiError =
      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(
      apiError, new HttpHeaders(), apiError.getStatus());
}
*/

/*
    select * from users u
    where :name is null or u.name = :name
    and
 */
}
