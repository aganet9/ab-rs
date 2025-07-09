package ru.alfabeton.request.exception;

public class ProductCategoryNotMatchException extends RuntimeException {
    public ProductCategoryNotMatchException(String message) {
        super(message);
    }
}
