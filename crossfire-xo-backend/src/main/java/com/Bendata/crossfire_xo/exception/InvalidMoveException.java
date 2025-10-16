package com.Bendata.crossfire_xo.exception;


public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}