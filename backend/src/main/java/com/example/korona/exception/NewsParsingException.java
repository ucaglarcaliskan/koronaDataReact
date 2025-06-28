package com.example.korona.exception;

public class NewsParsingException extends RuntimeException {
    public NewsParsingException(String message) {
        super(message);
    }
}