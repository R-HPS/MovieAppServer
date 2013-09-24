package jp.recruit.hps.movie.server.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class RegisterResultV1Dto extends ResultV1Dto {
    private List<String> errorList = new ArrayList<String>();

    public List<String> getErrorList() {
        return errorList;
    }

    public void addError(String error) {
        errorList.add(error);
    }
}
