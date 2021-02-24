package com.leodelmiro.mercadolivre.builders;

import com.leodelmiro.mercadolivre.newcategory.Category;

public class CategoryBuilder {

    private String name;
    private Category motherCategory;

    public CategoryBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryBuilder withMotherCategory(Category motherCategory) {
        if (motherCategory != null) this.motherCategory = motherCategory;
        return this;
    }

    public Category build() {
        Category category = new Category(name);

        if (motherCategory != null) category.setMotherCategory(motherCategory);

        return category;
    }
}
