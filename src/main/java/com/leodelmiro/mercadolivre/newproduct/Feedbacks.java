package com.leodelmiro.mercadolivre.newproduct;

import com.leodelmiro.mercadolivre.newfeedback.Feedback;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Isola as operações sobre o conjunto de opiniões
 *
 */
public class Feedbacks {

    private Set<Feedback> feedbacks;

    public Feedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public <T> Set<T> mapFeedbacks(Function<Feedback,T> mapFunction) {
        return this.feedbacks.stream().map(mapFunction).collect(Collectors.toSet());
    }

    public Double average() {
        Set<Integer> ratings = mapFeedbacks(Feedback::getRating);
        OptionalDouble possibleAverage = ratings.stream().mapToInt(rating -> rating).average();
        return possibleAverage.orElseGet(() -> 0.0);
    }

    public int total() {
        return this.feedbacks.size();
    }
}
