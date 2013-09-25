package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.QuestionV1Dto;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Question;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.QuestionService;
import jp.recruit.hps.movie.server.service.SelectionService;
import jp.recruit.hps.movie.server.service.UserService;

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

    public ResultV1Dto createQuestion(@Named("userKey") String userKey,
            @Named("selectionKey") String selectionKey,
            @Named("name") String name) {
        ResultV1Dto result = new ResultV1Dto();
        User user = UserService.getUserByKey(Datastore.stringToKey(userKey));
        Selection selection =
            SelectionService.getSelection(Datastore.stringToKey(selectionKey));
        try {
            if (user == null) {
                logger.warning("user not found");
                result.setResult(FAIL);
            } else if (selection == null) {
                logger.warning("company not found");
                result.setResult(FAIL);
            } else {
                QuestionService.createQuestion(user, selection, name);
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }

    public List<QuestionV1Dto> getQuestions(
            @Named("selectionKey") String selectionKey) {
        List<QuestionV1Dto> resultList = new ArrayList<QuestionV1Dto>();
        List<Question> questionList =
            QuestionService.getQuestionListBySelectionKey(Datastore
                .stringToKey(selectionKey));
        for (Question question : questionList) {
            QuestionV1Dto dto = new QuestionV1Dto();
            dto.setKey(Datastore.keyToString(question.getKey()));
            dto.setName(question.getName());
            resultList.add(dto);
        }
        return resultList;
    }

}
