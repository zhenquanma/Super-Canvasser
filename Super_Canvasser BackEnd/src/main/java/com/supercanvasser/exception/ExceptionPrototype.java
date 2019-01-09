package com.supercanvasser.exception;

public class ExceptionPrototype extends RuntimeException {

    public ExceptionPrototype(String message) {
        super(message);
    }

    public ExceptionPrototype(String message, Throwable cause) {
        super(message, cause);
    }
}
