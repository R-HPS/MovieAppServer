package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.QuestionResultV1Dto;
import jp.recruit.hps.movie.server.api.dto.QuestionV1Dto;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Question;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.QuestionService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "questionEndpoint", version = "v1")
public class QuestionV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(InterviewV1EndPoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public QuestionResultV1Dto createQuestion(
            @Named("interviewKey") String interviewKey,
            @Named("name") String name) {
        QuestionResultV1Dto result = new QuestionResultV1Dto();
        Interview interview = InterviewService.getInterview(Datastore.stringToKey(interviewKey));
        try {
            if (interview == null) {
                logger.warning("interview not found");
                result.setResult(FAIL);
            } else {
                User user = interview.getUserRef().getModel();
                Company company = interview.getCompanyRef().getModel();
                Question question = QuestionService.createQuestion(user, company, name);
                result.setResult(SUCCESS);
                QuestionV1Dto dto = new QuestionV1Dto();
                dto.setKey(Datastore.keyToString(question.getKey()));
                dto.setName(name);
                result.setQuestion(dto);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }

    public List<QuestionV1Dto> getQuestions(
            @Named("companyKey") String companyKey) {
        List<QuestionV1Dto> resultList = new ArrayList<QuestionV1Dto>();
        List<Question> questionList =
            QuestionService.getQuestionListByCompanyKey(Datastore
                .stringToKey(companyKey));
        for (Question question : questionList) {
            QuestionV1Dto dto = new QuestionV1Dto();
            dto.setKey(Datastore.keyToString(question.getKey()));
            dto.setName(question.getName());
            resultList.add(dto);
        }
        return resultList;
    }

}
