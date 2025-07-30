package com.myapp.desk.exception;

public class InstrumentNotFoundException extends RuntimeException {
    public InstrumentNotFoundException(Long id) {
        super("Instrument with ID " + id + " not found");
    }
}
