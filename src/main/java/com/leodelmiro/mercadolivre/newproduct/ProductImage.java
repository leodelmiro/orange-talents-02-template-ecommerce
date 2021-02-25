package com.leodelmiro.mercadolivre.newproduct;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tb_product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @Valid
    private Product product;

    @URL
    @NotBlank
    private String link;

    @Deprecated
    public ProductImage() {

    }

    public ProductImage(@NotNull @Valid Product product, @URL @NotBlank String link) {
        this.product = product;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(product, that.product) && Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, link);
    }
}
