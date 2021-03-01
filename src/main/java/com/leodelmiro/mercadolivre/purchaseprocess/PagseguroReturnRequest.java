package com.leodelmiro.mercadolivre.purchaseprocess;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroReturnRequest implements ReturnPaymentGateway{

    @NotBlank
    private String transactionId;

    @NotNull
    private PurchaseStatusPagseguro status;

    public PagseguroReturnRequest(@NotBlank String transactionId, @NotNull PurchaseStatusPagseguro status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PagseguroReturnRequest{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                '}';
    }

    public Transaction toTransaction(Purchase purchase) {
        return new Transaction(status.normalize(), transactionId, purchase);
    }

}
