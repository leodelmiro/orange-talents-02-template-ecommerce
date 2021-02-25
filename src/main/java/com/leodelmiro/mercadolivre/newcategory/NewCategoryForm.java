package com.leodelmiro.mercadolivre.newcategory;

import com.leodelmiro.mercadolivre.common.validation.CategoryNotFoundException;
import com.leodelmiro.mercadolivre.common.validation.UniqueValue;
import com.sun.istack.Nullable;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NewCategoryForm {

    @NotBlank
    @UniqueValue(fieldName = "name", domainClass = Category.class)
    private String name;

    @Nullable
    private Long motherCategoryId;

    public void setName(String name) {
        this.name = name;
    }

    public void setMotherCategoryId(Long motherCategoryId) {
        this.motherCategoryId = motherCategoryId;
    }

    public String getName() {
        return name;
    }

    public Long getMotherCategoryId() {
        return motherCategoryId;
    }

    public Category toModel(EntityManager entityManager) {
        Category newCategory = new Category(name);

        if (motherCategoryId != null) {
            Category motherCategory = entityManager.find(Category.class, motherCategoryId);

            if (motherCategory == null) throw new CategoryNotFoundException("Categoria mãe informada não é válida");

            newCategory.setMotherCategory(motherCategory);
        }

        return newCategory;
    }

}
