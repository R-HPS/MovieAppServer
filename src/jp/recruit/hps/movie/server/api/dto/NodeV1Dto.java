package jp.recruit.hps.movie.server.api.dto;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class NodeV1Dto {
    private List<NodeV1Dto> childNodes;
    private String question;
    private String answer;

    public List<NodeV1Dto> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<NodeV1Dto> childNodes) {
        this.childNodes = childNodes;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
