package com.leodelmiro.mercadolivre.newfeedback;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class NewFeedbackForm {

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @Min(1)
    @Max(5)
    private Short rating;

    public NewFeedbackForm(@NotBlank String title, @NotBlank String description, @Min(1) @Max(5) Short rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Short getRating() {
        return rating;
    }

    public Feedback toModel(@NotNull @Valid Product product, @NotNull @Valid User consumer) {
        return new Feedback(title, description, rating, product, consumer);
    }
}
