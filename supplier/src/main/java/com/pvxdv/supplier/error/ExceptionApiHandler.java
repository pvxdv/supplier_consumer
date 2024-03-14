package com.pvxdv.supplier.error;

import com.pvxdv.supplier.exception.ResourceAlreadyExistException;
import com.pvxdv.supplier.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundExceptionResolve(ResourceNotFoundException e){
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(new Date(),e.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> resourceAlreadyExistExceptionResolve(ResourceAlreadyExistException e){
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(new Date(),e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(MethodArgumentNotValidException e){
        String validationErrorMassage = "Validation error. " + e.getBindingResult().getFieldErrors().stream()
                .map(error -> "%s:%s".formatted(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        log.error(validationErrorMassage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(new Date(), validationErrorMassage));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(ConstraintViolationException e){
        String validationErrorMassage = "Validation error. " + e.getConstraintViolations().stream()
                .map(violation -> "%s:%s".formatted(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.joining(", "));
        log.error(validationErrorMassage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(new Date(),validationErrorMassage));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidExceptionResolve(HandlerMethodValidationException e){
        //todo create readable response
        String validationErrorMassage = "Validation error. " + e.getAllErrors();
        log.error(validationErrorMassage);
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorMessage(new Date(),validationErrorMassage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> resourceOtherExceptionResolve(Exception e){
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(new Date(),e.getMessage()));
    }
}
