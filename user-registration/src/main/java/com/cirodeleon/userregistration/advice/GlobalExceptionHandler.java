package com.cirodeleon.userregistration.advice;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cirodeleon.userregistration.exception.EmailAlreadyRegisteredException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {   
    	e.printStackTrace();
        return new ResponseEntity<>(Collections.singletonMap("mensaje", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleEmailAlreadyRegistered(EmailAlreadyRegisteredException ex) {
        Map<String, Object> body = new LinkedHashMap<>();       
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
            .map(error -> {
                String fieldName = ((FieldError) error).getField();
                String message = error.getDefaultMessage();
                return fieldName + ": " + message;
            })
            .collect(Collectors.joining(", "));

       
        return new ResponseEntity<>(Collections.singletonMap("message", errorMessage), HttpStatus.BAD_REQUEST);
    }
}

