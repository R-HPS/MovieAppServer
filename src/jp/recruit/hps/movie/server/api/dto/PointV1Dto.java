package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class PointV1Dto {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
