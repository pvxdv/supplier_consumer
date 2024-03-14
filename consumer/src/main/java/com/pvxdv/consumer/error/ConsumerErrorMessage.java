package com.pvxdv.consumer.error;

import lombok.Getter;

import java.util.Date;


@Getter
public class ConsumerErrorMessage extends  ErrorMessage{
    private final String path;
    public ConsumerErrorMessage(Date timestamp, String message, String path) {
        super(timestamp, message);
        this.path = path;
    }
}
