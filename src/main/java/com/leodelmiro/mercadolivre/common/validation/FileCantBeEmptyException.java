package com.leodelmiro.mercadolivre.common.validation;

public class FileCantBeEmptyException extends RuntimeException {

    public FileCantBeEmptyException(String message) {
        super(message);
    }
}
