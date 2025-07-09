package ru.alfabeton.request.exception;

public class ProductInUseException extends RuntimeException {
    public ProductInUseException(Long id) {
        super("Product with id " + id + " is used in request and cannot be deleted");
    }
}
