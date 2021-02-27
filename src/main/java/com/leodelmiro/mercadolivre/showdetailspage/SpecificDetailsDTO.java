package com.leodelmiro.mercadolivre.showdetailspage;

import com.leodelmiro.mercadolivre.newproduct.Specific;

public class SpecificDetailsDTO {

    private String name;
    private String description;

    public SpecificDetailsDTO(Specific entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
