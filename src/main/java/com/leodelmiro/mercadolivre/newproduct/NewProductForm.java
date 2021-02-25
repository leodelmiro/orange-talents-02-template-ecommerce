package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.common.validation.ExistsId;
import com.leodelmiro.mercadolivre.common.validation.UniqueValue;
import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newuser.User;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewProductForm {

    @NotBlank
    @UniqueValue(domainClass = Product.class, fieldName = "name")
    private String name;

    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private Long quantity;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @ExistsId(fieldName = "id", domainClass = Category.class)
    private Long categoryId;

    @Size(min = 3)
    @Valid
    private List<NewSpecificForm> specifics = new ArrayList<>();

    public NewProductForm(@NotBlank String name,
                          @Positive BigDecimal price,
                          @PositiveOrZero Long quantity,
                          @NotBlank @Size(max = 1000) String description,
                          @NotNull Long categoryId,
                          List<NewSpecificForm> specifics) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.specifics.addAll(specifics);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<NewSpecificForm> getSpecifics() {
        return specifics;
    }

    public Product toModel(EntityManager entityManager, User owner) {
        Category category = entityManager.find(Category.class, categoryId);

        return new Product(name, price, quantity, description, category, owner, specifics);
    }

    public Set<String> searchForSpecificsEquals() {
        HashSet<String> equalsNames = new HashSet<>();
        HashSet<String> results = new HashSet<>();

        for (NewSpecificForm specific: specifics) {
            String name = specific.getName();
            if (!equalsNames.add(name)) {
                results.add(name);
            }
        }
        return results;
    }

}
