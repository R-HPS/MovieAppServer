package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class CompanyV1Dto {
    private String key;
    
    private String name;
    
    private String phase;
    
    private boolean wasRead;
    
    private Long startDate;

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

    public boolean isWasRead() {
        return wasRead;
    }

    public void setWasRead(boolean wasRead) {
        this.wasRead = wasRead;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }
}
