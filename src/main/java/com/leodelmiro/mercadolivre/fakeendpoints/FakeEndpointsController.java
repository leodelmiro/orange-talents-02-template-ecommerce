package com.leodelmiro.mercadolivre.fakeendpoints;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class FakeEndpointsController {

    @PostMapping(value = "/invoices")
    public void createInvoice(@Valid @RequestBody NewPurchaseInvoiceRequest request) throws InterruptedException {
        System.out.println("criando nota para " + request.getPurchaseId() + " do comprador " + request.getPurchaserId());
        Thread.sleep(150);
    }

    @PostMapping(value = "/ranking")
    public void ranking(@Valid @RequestBody RankingPurchaseRequest request) throws InterruptedException {
        System.out.println("Parabéns vendedor: " + request.getProductOwnerId() + " voce acaba de realizar a venda " + request.getPurchaseId());
        Thread.sleep(150);
    }
}
