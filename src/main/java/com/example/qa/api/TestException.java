package com.example.qa.api;

public class TestException extends RuntimeException {

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }

    public TestException(String message, Object... args) {
        super(String.format(message, args));
    }
}
