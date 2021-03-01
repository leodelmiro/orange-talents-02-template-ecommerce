package com.leodelmiro.mercadolivre.fakeendpoints;

import com.leodelmiro.mercadolivre.newquestion.Mailer;
import com.leodelmiro.mercadolivre.newquestion.Question;
import com.leodelmiro.mercadolivre.purchaseprocess.Purchase;
import com.leodelmiro.mercadolivre.purchaseprocess.ReturnPaymentGateway;
import com.leodelmiro.mercadolivre.purchaseprocess.SuccessPurchaseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails implements SuccessPurchaseEvent {

    @Autowired
    private Mailer mailer;

    public void newQuestion(@NotNull @Valid Question question) {
        mailer.send("<html>...</html>",
                "Nova pergunta...",
                "novapergunta@nossomercadolivre.com",
                question.getQuestioner().getUsername(),
                question.getProductOwner().getUsername());
    }

    public void newPurchase(Purchase newPurchase) {
        mailer.send("Nova venda..." + newPurchase, "Você tem uma nova venda",
                newPurchase.getPurchaser().getUsername(),
                "compras@nossomercadolivre.com",
                newPurchase.getPurchaser().getUsername());
    }

    @Override
    public void process(Purchase purchase) {
        mailer.send("Nova compra..." + purchase, "Você realizou uma nova compra",
                purchase.getPurchaser().getUsername(),
                "compras@nossomercadolivre.com",
                purchase.getPurchaser().getUsername());
    }

    public void failPayment(Purchase purchase) {
        mailer.send("Tentativa de compra falha, por favor tente novamente!" , "Você tentou realizar uma nova compra",
                purchase.getPurchaser().getUsername(),
                "compras@nossomercadolivre.com",
                purchase.getPurchaser().getUsername());
    }
}
