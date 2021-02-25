package com.leodelmiro.mercadolivre.newproduct;

public class CreatedSpecificsDTO {

    private Long id;
    private String name;
    private String description;

    public CreatedSpecificsDTO(Specific entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
