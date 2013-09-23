package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

import jp.recruit.hps.movie.server.model.Interview.Atmosphere;
import jp.recruit.hps.movie.server.model.Interview.Category;

@Entity
public class InterviewV1Dto {
    private Long startDate;
    private Long endDate;
    private String question;
    private Atmosphere atmosphere;
    private Category category;

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
