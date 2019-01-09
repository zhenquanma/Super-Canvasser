package com.supercanvasser.exception;

public class UsernameExistedException extends ExceptionPrototype {

    private static final long serialVersionUID = 1L;

    public UsernameExistedException(String msg) {
        super(msg);
    }

    public UsernameExistedException(String msg, Throwable t) {
        super(msg, t);
    }
}
