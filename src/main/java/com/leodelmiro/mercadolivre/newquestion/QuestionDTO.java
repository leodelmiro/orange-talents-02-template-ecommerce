package com.leodelmiro.mercadolivre.newquestion;

import java.time.Instant;

public class QuestionDTO implements Comparable<QuestionDTO>{

    private Long id;
    private String title;
    private Long questionerId;
    private Instant createdAt;

    public QuestionDTO(Question entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.questionerId = entity.getQuestioner().getId();
        this.createdAt = entity.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getUserId() {
        return questionerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public int compareTo(QuestionDTO questionDTO) {
        return this.title.compareTo(questionDTO.getTitle());
    }
}
