package com.leodelmiro.mercadolivre.newfeedback;

public class CreatedFeedbackDTO {

    private Long id;
    private String title;
    private String description;
    private Short rating;
    private Long productId;
    private Long userId;

    public CreatedFeedbackDTO(Feedback entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.rating = entity.getRating();
        this.productId = entity.getProduct().getId();
        this.userId = entity.getUser().getId();
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

    public Short getRating() {
        return rating;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getUserId() {
        return userId;
    }
}
