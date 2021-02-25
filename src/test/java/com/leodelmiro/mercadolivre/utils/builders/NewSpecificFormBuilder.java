package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;

public class NewSpecificFormBuilder {

    private String name;
    private String description;

    public NewSpecificFormBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NewSpecificFormBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public NewSpecificForm build() {
        return new NewSpecificForm(name, description);
    }
}
