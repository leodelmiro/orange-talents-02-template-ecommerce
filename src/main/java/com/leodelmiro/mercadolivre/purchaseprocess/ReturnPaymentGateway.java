package com.leodelmiro.mercadolivre.purchaseprocess;

public interface ReturnPaymentGateway {

    /**
     *
     * @param purchase
     * @return uma nova transação em função do gateway escolhido
     */
    Transaction toTransaction(Purchase purchase);

}
