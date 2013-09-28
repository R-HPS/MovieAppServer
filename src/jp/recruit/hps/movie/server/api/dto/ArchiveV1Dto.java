package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class ArchiveV1Dto {
    private String type;
    
    private int year;
    
    private String body;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
   

}
