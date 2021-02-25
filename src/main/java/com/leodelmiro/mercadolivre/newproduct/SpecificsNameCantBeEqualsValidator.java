package com.leodelmiro.mercadolivre.newproduct;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class SpecificsNameCantBeEqualsValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NewProductForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()){
            return;
        }

        NewProductForm newProductForm = (NewProductForm) target;
        Set<String> equalsName = newProductForm.searchForSpecificsEquals();
        if (!equalsName.isEmpty()){
            errors.rejectValue("specifics", null, "Você tem características iguais!");
        }
    }
}
