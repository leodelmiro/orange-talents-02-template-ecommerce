package com.leodelmiro.mercadolivre.product;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewSpecificForm {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public NewSpecificForm(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Specific toModel(@NotNull @Valid Product product) {
        return new Specific(name, description, product);
    }
}
