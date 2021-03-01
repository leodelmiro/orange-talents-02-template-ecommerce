package com.leodelmiro.mercadolivre.fakeendpoints;

import javax.validation.constraints.NotNull;

public class RankingPurchaseRequest {

    @NotNull
    private Long purchaseId;

    @NotNull
    private Long productOwnerId;

    public RankingPurchaseRequest(@NotNull Long purchaseId, @NotNull Long productOwnerId) {
        this.purchaseId = purchaseId;
        this.productOwnerId = productOwnerId;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getProductOwnerId() {
        return productOwnerId;
    }

}
