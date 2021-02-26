package com.leodelmiro.mercadolivre.newquestion;

import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newuser.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "tb_questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "questioner_id")
    private User questioner;

    @Column(updatable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt = Instant.now();

    @Deprecated
    public Question(){

    }

    public Question(@NotBlank String title, @NotNull Product product, @NotNull User questioner) {
        this.title = title;
        this.product = product;
        this.questioner = questioner;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Product getProduct() {
        return product;
    }

    public User getQuestioner() {
        return questioner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public User getProductOwner() {
        return product.getOwner();
    }
}
