package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.common.validation.ExistsId;
import com.leodelmiro.mercadolivre.newproduct.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseForm {

    @Positive
    private Long quantity;

    @NotNull
    @ExistsId(fieldName = "id", domainClass = Product.class)
    private Long productId;

    @NotNull
    private PaymentGateway gateway;

    public NewPurchaseForm(@Positive Long quantity, @NotNull Long productId, @NotNull PaymentGateway gateway) {
        this.quantity = quantity;
        this.productId = productId;
        this.gateway = gateway;
    }

    public Long getQuantity() {
        return quantity;
    }


    public Long getProductId() {
        return productId;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }
}
