package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newcategory.NewCategoryForm;

public class NewCategoryFormBuilder {

    private String name;
    private Long motherCategoryId;

    public NewCategoryFormBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NewCategoryFormBuilder withMotherCategoryId(Long motherCategoryId) {
        this.motherCategoryId = motherCategoryId;
        return this;
    }

    public NewCategoryForm build() {
        NewCategoryForm newCategoryForm = new NewCategoryForm();
        newCategoryForm.setName(name);

        if (motherCategoryId != null) newCategoryForm.setMotherCategoryId(motherCategoryId);

        return newCategoryForm;
    }
}
