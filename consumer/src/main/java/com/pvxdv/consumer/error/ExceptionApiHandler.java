package com.pvxdv.consumer.error;

import com.pvxdv.consumer.exception.RestTemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<ErrorMessage> restTemplateExceptionResolve(RestTemplateException e){
        log.error(e.getMessage());
        return ResponseEntity
                .status(e.getStatus())
                .body(new ConsumerErrorMessage(new Date(),e.getMessage(), e.getPath()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> otherExceptionResolve(Exception e){
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(new Date(),e.getMessage()));
    }
}
