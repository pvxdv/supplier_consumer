package com.pvxdv.consumer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RestTemplateException extends RuntimeException{
    private final HttpStatusCode status;
    private final String path;
    public RestTemplateException(String message, HttpStatusCode status, String path) {
        super(message);
        this.status = status;
        this.path = path;

    }
}
