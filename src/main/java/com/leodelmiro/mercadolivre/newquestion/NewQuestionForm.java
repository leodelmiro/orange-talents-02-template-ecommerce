package com.leodelmiro.mercadolivre.newquestion;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;

import javax.validation.constraints.NotBlank;

public class NewQuestionForm {

    @NotBlank
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Question toModel(Product product, User possibleConsumer) {
        return new Question(title, product, possibleConsumer);
    }
}
