package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.newcategory.Category;
import com.leodelmiro.mercadolivre.newuser.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Positive
    private BigDecimal price;

    @PositiveOrZero
    private Long quantity;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<ProductImage> images = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Size(min = 3)
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<Specific> specifics = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @NotNull
    @PastOrPresent
    @Column(updatable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt = Instant.now();

    @Deprecated
    public Product() {

    }

    public Product(@NotBlank String name,
                   @Positive BigDecimal price,
                   @PositiveOrZero Long quantity,
                   @NotBlank @Size(max = 1000) String description,
                   @NotNull Category category,
                   @NotNull User owner,
                   Collection<NewSpecificForm> specifics) {
        Assert.hasLength(name, "Nome é obrigatório");
        Assert.state(price.compareTo(new BigDecimal("0.0")) >= 0, "Preço deve ser maior que 0");
        Assert.state(quantity >= 0, "Quantidade deve ser maior igual a zero");
        Assert.hasLength(description, "Descrição é obrigatória");
        Assert.state(description.length() < 1000, "Descrição deve ser menor que 1000 caracteres");
        Assert.notNull(category, "Categoria é obrigatória");
        Assert.notNull(owner, "Dono do produto é obrigatório");
        Assert.state(specifics.size() >= 3, "Número de características deve ser maior igual a 3");

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.specifics.addAll(specifics.stream().map(
                specific -> specific.toModel(this)).collect(Collectors.toSet()));

        Assert.isTrue(this.specifics.size() >= 3, "Todo produto precisa ter no mínimo 3 caracteristicas!");
    }

    public Long getId() {
        return id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<Specific> getSpecifics() {
        return specifics;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public User getOwner() {
        return owner;
    }

    public void associateImages(Set<String> links) {
        Set<ProductImage> images =
                links.stream().map(link -> new ProductImage(this, link)).collect(Collectors.toSet());

        this.images.addAll(images);
    }

    public boolean belongsToUser(User possibleOwner) {
        return this.owner.equals(possibleOwner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
