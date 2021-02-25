package com.leodelmiro.mercadolivre.common.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionError {

    @Autowired
    private MessageSource messageSource;
    private IllegalArgumentException exception;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldMessageDTO> validation(MethodArgumentNotValidException exception) {
        List<FieldMessageDTO> fieldMessageDTOS = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            FieldMessageDTO error = new FieldMessageDTO(e.getField(), message);
            fieldMessageDTOS.add(error);
        });

        return fieldMessageDTOS;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNotFoundException.class)
    public FieldMessageDTO validation(CategoryNotFoundException exception) {
        return new FieldMessageDTO("categoryId", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileCantBeEmptyException.class)
    public FieldMessageDTO validation(FileCantBeEmptyException exception) {
        return new FieldMessageDTO("file", exception.getMessage());
    }
}
