package com.leodelmiro.mercadolivre.purchaseprocess;

public enum PurchaseStatusPagseguro {
    SUCCESS, FAIL;

    public PurchaseStatus normalize() {
        if (this.equals(SUCCESS)) {
            return PurchaseStatus.SUCCESS;
        }
        return PurchaseStatus.FAIL;
    }
}
