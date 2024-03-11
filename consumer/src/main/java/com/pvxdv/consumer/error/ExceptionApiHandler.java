package com.pvxdv.consumer.error;


import com.pvxdv.consumer.exception.UnexpectedHTTPStatusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(UnexpectedHTTPStatusException.class)
    public ResponseEntity<ErrorMessage> unexpectedHTTPStatusExceptionResolve(UnexpectedHTTPStatusException e, WebRequest request){
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(new Date(),e.getMessage(),
                        request.getDescription(false), e.getSupplierServiceStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> resourceAlreadyExistExceptionResolve(Exception e, WebRequest request){
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(new Date(),e.getMessage(),
                        request.getDescription(false), null));
    }
}
