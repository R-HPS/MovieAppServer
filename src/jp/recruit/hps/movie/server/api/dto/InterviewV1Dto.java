package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

import jp.recruit.hps.movie.server.model.Interview.Atmosphere;
import jp.recruit.hps.movie.server.model.Interview.Category;

@Entity
public class InterviewV1Dto {
    private Long startDate;
    private int duration;
    private String question;
    private Atmosphere atmosphere;
    private Category category;

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
