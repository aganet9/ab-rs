package ru.alfabeton.request.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long id) {
        super("Request not found with id: " + id);
    }
}
