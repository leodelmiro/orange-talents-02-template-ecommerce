package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import com.leodelmiro.mercadolivre.purchaseprocess.*;

public class PurchaseBuilder {

    private Product chosenProduct;
    private Long quantity;
    private User purchaser;
    private PaymentGateway gateway;
    private PurchaseStatus status;

    public PurchaseBuilder withChosenProduct(Product chosenProduct) {
        this.chosenProduct = chosenProduct;
        return this;
    }

    public PurchaseBuilder withQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public PurchaseBuilder withPurchaser(User purchaser) {
        this.purchaser = purchaser;
        return this;
    }

    public PurchaseBuilder withPaymentGateway(PaymentGateway gateway) {
        this.gateway = gateway;
        return this;
    }

    public PurchaseBuilder withPurchaseStatus(PurchaseStatus status) {
        this.status = status;
        return this;
    }

    public Purchase buildWithTransaction() {
        Purchase purchaseWithTransaction = new Purchase(chosenProduct, quantity, purchaser, gateway, status);

        ReturnPaymentGateway successReturn = (purchase -> {
            return new Transaction(PurchaseStatus.SUCCESS, "1", purchase);
        });
        purchaseWithTransaction.addTransaction(successReturn);

        return purchaseWithTransaction;
    }

    public Purchase build() {
        return new Purchase(chosenProduct, quantity, purchaser, gateway, status);
    }

}
