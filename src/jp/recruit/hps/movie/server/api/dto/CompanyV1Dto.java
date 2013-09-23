package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class CompanyV1Dto {
    private String key;
    
    private String name;
    
    private String phase;

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

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
