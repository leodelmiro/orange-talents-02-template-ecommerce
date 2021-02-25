package com.leodelmiro.mercadolivre.newproduct;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tb_specifics")
public class Specific {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @ManyToOne
    @JoinTable(name="products_specifics", joinColumns=
            {@JoinColumn(name="specifics_id")}, inverseJoinColumns=
            {@JoinColumn(name="products_id")})
    private Product product;

    public Specific(String name, String description, Product product) {
        Assert.hasLength(name, "Nome de característica é obrigatório!");
        Assert.hasLength(description, "Descrição é obrigatória!");
        Assert.notNull(product, "Produto é obrigatório!");

        this.name = name;
        this.description = description;
        this.product = product;
    }

    @Deprecated
    public Specific() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specific specific = (Specific) o;
        return Objects.equals(name, specific.name) && Objects.equals(product, specific.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, product);
    }
}
