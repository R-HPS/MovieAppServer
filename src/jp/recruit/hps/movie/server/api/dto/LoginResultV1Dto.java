package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class LoginResultV1Dto {
    
    private String email;
    
    private String result;

    private Long id;

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
}
