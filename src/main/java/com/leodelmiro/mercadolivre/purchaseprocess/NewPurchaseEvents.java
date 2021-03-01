package com.leodelmiro.mercadolivre.purchaseprocess;

import com.leodelmiro.mercadolivre.fakeendpoints.Emails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NewPurchaseEvents {

    private Set<SuccessPurchaseEvent> successPurchaseEvents;

    private Emails emails;

    public NewPurchaseEvents(Set<SuccessPurchaseEvent> successPurchaseEvents, Emails emails) {
        this.successPurchaseEvents = successPurchaseEvents;
        this.emails = emails;
    }

    public void process(Purchase purchase) {
        if (purchase.successfullyProcessed()) {
            purchase.setStatus(PurchaseStatus.SUCCESS);
            successPurchaseEvents.forEach(event -> event.process(purchase));
        } else {
            emails.failPayment(purchase);
        }
    }
}
