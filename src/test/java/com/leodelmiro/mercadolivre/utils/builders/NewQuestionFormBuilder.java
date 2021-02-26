package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newquestion.NewQuestionForm;
import com.leodelmiro.mercadolivre.newquestion.Question;

public class NewQuestionFormBuilder {

    private String title;

    public NewQuestionFormBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public NewQuestionForm build(){
        NewQuestionForm newQuestionForm = new NewQuestionForm();
        newQuestionForm.setTitle(title);
        return newQuestionForm;
    }
}
