package com.leodelmiro.mercadolivre.newcategory;

public class CreatedCategoryDTO {

    private Long id;
    private String name;
    private Long motherCategoryId;

    public CreatedCategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        if (entity.getMotherCategory() != null) this.motherCategoryId = entity.getMotherCategory().getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getMotherCategoryId() {
        return motherCategoryId;
    }
}
