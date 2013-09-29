package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class QuestionWithCountV1Dto {
    private String key;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
