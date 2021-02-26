package com.leodelmiro.mercadolivre.newquestion;

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
}
