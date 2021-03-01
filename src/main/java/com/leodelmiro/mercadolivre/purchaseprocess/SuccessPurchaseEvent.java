package com.leodelmiro.mercadolivre.purchaseprocess;

/**
 * Todo evento relacionado a compra realizada com sucesso deve implementar essa interface
 *
 */
public interface SuccessPurchaseEvent {

    void process(Purchase purchase);
}
