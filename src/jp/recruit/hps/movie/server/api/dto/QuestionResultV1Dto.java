package jp.recruit.hps.movie.server.api.dto;

import javax.persistence.Entity;

@Entity
public class QuestionResultV1Dto extends ResultV1Dto {
    private QuestionV1Dto question;

    public QuestionV1Dto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionV1Dto question) {
        this.question = question;
    }


}
