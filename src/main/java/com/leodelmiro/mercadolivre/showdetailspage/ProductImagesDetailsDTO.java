package com.leodelmiro.mercadolivre.showdetailspage;

import com.leodelmiro.mercadolivre.newproduct.ProductImage;

public class ProductImagesDetailsDTO {

    private String link;

    public ProductImagesDetailsDTO(ProductImage entity) {
        this.link = entity.getLink();
    }

    public String getLink() {
        return link;
    }

}
