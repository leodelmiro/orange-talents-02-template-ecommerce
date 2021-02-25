package com.leodelmiro.mercadolivre.newproduct;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class CreatedProductDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private Instant createdAt;
    private Set<CreatedSpecificsDTO> specifics;
    private Long ownerId;

    public CreatedProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.description = entity.getDescription();
        this.createdAt = entity.getCreatedAt();
        this.specifics = entity.getSpecifics().stream().map(CreatedSpecificsDTO::new).collect(Collectors.toSet());
        this.ownerId = entity.getOwner().getId();
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

    public Set<CreatedSpecificsDTO> getSpecifics() {
        return specifics;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
