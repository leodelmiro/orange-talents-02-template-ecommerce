package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import io.jsonwebtoken.lang.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tb_purchases")
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @Valid
    private Product chosenProduct;

    @Positive
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "purchaser_id")
    @NotNull
    @Valid
    private User purchaser;

    @Enumerated
    @NotNull
    PaymentGateway gateway;

    @NotNull
    PurchaseStatus status;

    @Deprecated
    public Purchase() {
    }

    public Purchase(@NotNull @Valid Product chosenProduct, @Positive long quantity,
                    @NotNull @Valid User purchaser, @NotNull PaymentGateway gateway,
                    @NotNull PurchaseStatus status) {
        Assert.notNull(chosenProduct, "Produto não pode ser nulo!");
        Assert.state(quantity > 0, "Quantidade deve ser maior que 0!");
        Assert.notNull(purchaser, "Comprador não pode ser nulo!");
        Assert.notNull(gateway, "gateway não pode ser nulo!");
        Assert.notNull(status, "Status da compra não pode ser nulo!");

        this.chosenProduct = chosenProduct;
        this.quantity = quantity;
        this.purchaser = purchaser;
        this.gateway = gateway;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Product getChosenProduct() {
        return chosenProduct;
    }

    public Long getQuantity() {
        return quantity;
    }

    public User getPurchaser() {
        return purchaser;
    }

    public String redirectUrl(
            UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.createUrlReturn(this, uriComponentsBuilder);
    }
}
