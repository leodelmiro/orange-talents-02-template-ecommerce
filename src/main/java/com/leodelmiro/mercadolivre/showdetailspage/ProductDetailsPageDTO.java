package com.leodelmiro.mercadolivre.showdetailspage;

import com.leodelmiro.mercadolivre.newfeedback.Feedback;
import com.leodelmiro.mercadolivre.newproduct.Feedbacks;
import com.leodelmiro.mercadolivre.newproduct.Product;
import com.leodelmiro.mercadolivre.newproduct.ProductImage;
import com.leodelmiro.mercadolivre.newquestion.Question;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;

public class ProductDetailsPageDTO {

    private String name;
    private BigDecimal price;
    private String description;
    private Set<SpecificDetailsDTO> specifics;
    private Set<String> imageLinks;
    private SortedSet<String> questions;
    private Set<Map<String, String>> feedbacks;
    private Double ratingAverage;
    private Integer totalRating;


    public ProductDetailsPageDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.specifics = product.mapSpecifics(SpecificDetailsDTO::new);
        this.imageLinks = product.mapImages(ProductImage::getLink);
        this.questions = product.mapQuestions(Question::getTitle);

        Feedbacks feedbacks = product.getFeedbacks();

        this.feedbacks = feedbacks.mapFeedbacks(feedback ->
                Map.of("title", feedback.getTitle(), "description", feedback.getDescription()));

        this.ratingAverage = feedbacks.average();
        this.totalRating = feedbacks.total();

    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Set<SpecificDetailsDTO> getSpecifics() {
        return specifics;
    }

    public Set<String> getImageLinks() {
        return imageLinks;
    }

    public Set<String> getQuestions() {
        return questions;
    }

    public Set<Map<String, String>> getFeedbacks() {
        return feedbacks;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public Integer getTotalRating() {
        return totalRating;
    }
}
