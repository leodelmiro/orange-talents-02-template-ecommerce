package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.purchaseprocess.NewPurchaseForm;
import com.leodelmiro.mercadolivre.purchaseprocess.PaymentGateway;

public class NewPurchaseFormBuilder {

    private Long quantity;
    private Long productId;
    private PaymentGateway gateway;

    public NewPurchaseFormBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public NewPurchaseFormBuilder withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public NewPurchaseFormBuilder withGateway(PaymentGateway gateway) {
        this.gateway = gateway;
        return this;
    }

    public NewPurchaseForm build() {
        return new NewPurchaseForm(quantity, productId, gateway);
    }
}
