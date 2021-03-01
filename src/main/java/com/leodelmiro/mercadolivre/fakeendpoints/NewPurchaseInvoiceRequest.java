package com.leodelmiro.mercadolivre.fakeendpoints;

import javax.validation.constraints.NotNull;

public class NewPurchaseInvoiceRequest {

    @NotNull
    private Long purchaseId;

    @NotNull
    private Long purchaserId;

    public NewPurchaseInvoiceRequest(@NotNull Long purchaseId, @NotNull Long purchaserId) {
        this.purchaseId = purchaseId;
        this.purchaserId = purchaserId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

}
