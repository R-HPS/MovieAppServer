package jp.recruit.hps.movie.server.api.dto;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class InterviewV1Dto {
    private double durationAvg;
    private double atmosphereAvg;
    private int category;
    private List<QuestionWithCountV1Dto> questionList;

    public double getDurationAvg() {
        return durationAvg;
    }

    public void setDurationAvg(double durationAvg) {
        this.durationAvg = durationAvg;
    }

    public double getAtmosphereAvg() {
        return atmosphereAvg;
    }

    public void setAtmosphereAvg(double atmosphereAvg) {
        this.atmosphereAvg = atmosphereAvg;
    }

    public List<QuestionWithCountV1Dto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionWithCountV1Dto> questionList) {
        this.questionList = questionList;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

}
