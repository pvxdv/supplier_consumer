package com.pvxdv.consumer.exception;

import lombok.Getter;

@Getter
public class UnexpectedHTTPStatusException extends RuntimeException{
    String supplierServiceStatusCode;
    public UnexpectedHTTPStatusException(String message, String supplierServiceStatusCode) {
        super(message);
        this.supplierServiceStatusCode = supplierServiceStatusCode;

    }
}
