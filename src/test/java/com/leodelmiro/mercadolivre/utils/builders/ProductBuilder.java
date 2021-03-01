package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newproduct.NewSpecificForm;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newproduct.Specific;
import com.leodelmiro.mercadolivre.newuser.User;

import java.math.BigDecimal;
import java.util.List;

public class ProductBuilder {

    private String name;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private Category category;
    private User owner;
    private List<NewSpecificForm> specifics;

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public ProductBuilder withOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public ProductBuilder withSpecifics(List<NewSpecificForm> specifics) {
        this.specifics = specifics;
        return this;
    }

    public Product build() {
        return new Product(name, price,quantity,description, category, owner, specifics);
    }
}
