package com.testvagrant.booknamechallenge.findthatbook.utils;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String err) {
        super(err);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
