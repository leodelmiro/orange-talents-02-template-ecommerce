package com.leodelmiro.mercadolivre.utils.builders;

import com.leodelmiro.mercadolivre.newfeedback.NewFeedbackForm;

public class NewFeedbackFormBuilder {

    private String title;
    private String description;
    private Short rating;

    public NewFeedbackFormBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public NewFeedbackFormBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public NewFeedbackFormBuilder withRating(Short rating) {
        this.rating = rating;
        return this;
    }

    public NewFeedbackForm build() {
        return new NewFeedbackForm(title, description, rating);
    }

}
