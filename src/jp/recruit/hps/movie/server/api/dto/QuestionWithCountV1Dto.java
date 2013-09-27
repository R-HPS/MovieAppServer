package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class QuestionWithCountV1Dto extends QuestionV1Dto {
    private double percent;
    private int count;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
