package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.common.validation.PurchaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class PurchaseProcessPart2Controller {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private NewPurchaseEvents newPurchaseEvents;

    @PostMapping(value = "/retorno-pagseguro/{purchaseId}")
    @Transactional
    public String pagseguroProcessing(@PathVariable Long purchaseId, @Valid PagseguroReturnRequest request) {
        return process(purchaseId, request);
    }

    @PostMapping(value = "/retorno-paypal/{purchaseId}")
    @Transactional
    public String paypalProcessing(@PathVariable Long purchaseId, @Valid PaypalReturnRequest request) {
        return process(purchaseId, request);
    }

    private String process(Long purchaseId, ReturnPaymentGateway returnPaymentGateway) {
        Purchase purchase = entityManager.find(Purchase.class, purchaseId);
        if (purchase == null) throw new PurchaseNotFoundException("Compra informada não encontrada");

        purchase.addTransaction(returnPaymentGateway);

        entityManager.merge(purchase);
        newPurchaseEvents.process(purchase);

        return returnPaymentGateway.toString();
    }
}
