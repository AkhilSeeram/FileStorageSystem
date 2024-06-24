package com.vcriate.demo.exceptions.advice;

import com.vcriate.demo.exceptions.EntityNotFoundException;
import com.vcriate.demo.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return new ResponseEntity<>(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return new ResponseEntity<>(userAlreadyExistsException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

}
