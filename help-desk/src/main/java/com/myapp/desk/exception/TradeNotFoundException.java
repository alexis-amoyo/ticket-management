package com.myapp.desk.exception;

public class TradeNotFoundException extends RuntimeException {
    public TradeNotFoundException(Long id) {
        super("Trade with ID " + id + " not found");
    }
}
