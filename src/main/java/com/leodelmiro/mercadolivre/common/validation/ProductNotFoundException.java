package com.leodelmiro.mercadolivre.common.validation;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
