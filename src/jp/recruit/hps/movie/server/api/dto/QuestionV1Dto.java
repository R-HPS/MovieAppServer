package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class QuestionV1Dto {
    private String key;
    
    private String name;

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
