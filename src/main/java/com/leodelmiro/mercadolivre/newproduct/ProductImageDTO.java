package com.leodelmiro.mercadolivre.newproduct;

public class ProductImageDTO {

    private String link;

    public ProductImageDTO(ProductImage entity) {
        this.link = entity.getLink();
    }

    public String getLink() {
        return link;
    }
}
