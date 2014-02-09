package jp.recruit.hps.movie.server.api.dto;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class InterviewV1Dto {
    private List<QuestionWithCountV1Dto> questionList;
    
    public List<QuestionWithCountV1Dto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionWithCountV1Dto> questionList) {
        this.questionList = questionList;
    }

}
