package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class ResultV1Dto {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
