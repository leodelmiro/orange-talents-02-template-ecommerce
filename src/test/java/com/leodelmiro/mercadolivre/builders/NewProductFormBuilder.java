package com.leodelmiro.mercadolivre.builders;

import com.leodelmiro.mercadolivre.newproduct.NewProductForm;
import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;
import com.leodelmiro.mercadolivre.newproduct.Product;

import java.math.BigDecimal;
import java.util.List;

public class NewProductFormBuilder {

    private String name;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private Long categoryId;
    private Long ownerId;
    private List<NewSpecificForm> specifics;

    public NewProductFormBuilder withName(String name){
        this.name = name;
        return this;
    }

    public NewProductFormBuilder withPrice(Double price) {
        this.price = new BigDecimal(price);
        return this;
    }

    public NewProductFormBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public NewProductFormBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public NewProductFormBuilder withCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public NewProductFormBuilder withOwnerId(Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public NewProductFormBuilder withSpecifics(List<NewSpecificForm> specifics) {
        this.specifics = specifics;
        return this;
    }

    public NewProductForm build(){
        return new NewProductForm(name, price, quantity, description, categoryId, ownerId, specifics);
    }
}
