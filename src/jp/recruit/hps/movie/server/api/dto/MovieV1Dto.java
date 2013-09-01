package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class MovieV1Dto {
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
