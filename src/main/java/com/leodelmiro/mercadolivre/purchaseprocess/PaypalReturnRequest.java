package com.leodelmiro.mercadolivre.purchaseprocess;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalReturnRequest implements ReturnPaymentGateway {

    @NotBlank
    private String transactionId;

    @Min(0) @Max(1)
    private int status;

    public PaypalReturnRequest(@NotBlank String transactionId, @Min(0) @Max(1) int status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public Transaction toTransaction(Purchase purchase) {
        return new Transaction(this.status == 0? PurchaseStatus.FAIL: PurchaseStatus.SUCCESS, transactionId, purchase);
    }
}
