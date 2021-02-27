package com.leodelmiro.mercadolivre.newfeedback;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "tb_feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Deprecated
    public Feedback() {

    }

    public Feedback(@NotBlank String title, @NotBlank @Size(max = 500) String description,
                    @Min(1) @Max(5) Integer rating, @NotNull Product product, @NotNull User user) {
        Assert.hasLength(title, "Título é obrigatório!");
        Assert.hasLength(description, "Descrição é obrigatória!");
        Assert.state(rating >= 1, "Nota deve ser maior ou igual a um!");
        Assert.state(rating <= 5, "Nota deve ser menor ou igual a cinco!");
        Assert.notNull(product, "Produto é obrigatório!");
        Assert.notNull(user, "Usuário é obrigatório!");

        this.title = title;
        this.description = description;
        this.rating = rating;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRating() {
        return rating;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(title, feedback.title) && Objects.equals(description, feedback.description) && Objects.equals(product, feedback.product) && Objects.equals(user, feedback.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, product, user);
    }
}
