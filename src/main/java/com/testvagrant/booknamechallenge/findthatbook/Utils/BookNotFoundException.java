package com.testvagrant.booknamechallenge.findthatbook.Utils;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String err) {
        super(err);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
