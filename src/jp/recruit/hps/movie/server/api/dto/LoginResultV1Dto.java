package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

/**
 *
 */
@Entity
public class LoginResultV1Dto {
    
    private String email;
    
    private String result;
    
    private String key;

    private Long id;
    
    private int point;

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
