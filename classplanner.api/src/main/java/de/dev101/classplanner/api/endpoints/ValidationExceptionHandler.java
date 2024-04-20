package de.dev101.classplanner.api.endpoints;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(RepositoryConstraintViolationException.class)
    public ResponseEntity<String> handle(RepositoryConstraintViolationException exception) {
        var message = exception.getMessage();
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}