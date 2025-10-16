package com.Bendata.crossfire_xo.exception;



public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}