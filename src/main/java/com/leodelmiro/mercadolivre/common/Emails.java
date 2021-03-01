package com.leodelmiro.mercadolivre.common;

import com.leodelmiro.mercadolivre.newquestion.Mailer;
import com.leodelmiro.mercadolivre.newquestion.Question;
import com.leodelmiro.mercadolivre.purchaseprocess.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class Emails {

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
}
