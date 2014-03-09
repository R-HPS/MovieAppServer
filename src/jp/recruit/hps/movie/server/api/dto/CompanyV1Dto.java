package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class CompanyV1Dto {
    private String key;
    
    private String interviewKey;
    
    private String name;
    
    private int interviewCount;
            
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getInterviewKey() {
        return interviewKey;
    }

    public void setInterviewKey(String interviewKey) {
        this.interviewKey = interviewKey;
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void setInterviewCount(int interviewCount) {
        this.interviewCount = interviewCount;
    }
}
