package com.leodelmiro.mercadolivre.fakeendpoints;

import com.leodelmiro.mercadolivre.purchaseprocess.Purchase;
import com.leodelmiro.mercadolivre.purchaseprocess.SuccessPurchaseEvent;
import io.jsonwebtoken.lang.Assert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Invoice implements SuccessPurchaseEvent {

    @Override
    public void process(Purchase purchase) {
        Assert.isTrue(purchase.successfullyProcessed(), "Compra não foi realizada com sucesso");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("purchaseId", purchase.getId(), "purchaserId", purchase.getPurchaser().getId());

        restTemplate.postForEntity("http://localhost:8080/invoices",request ,String.class);
    }
}
