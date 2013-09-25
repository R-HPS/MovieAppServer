package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.container.StringListContainer;
import jp.recruit.hps.movie.server.api.dto.InterviewV1Dto;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.Question;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.InterviewQuestionMapService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.QuestionService;
import jp.recruit.hps.movie.server.service.SelectionService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.datastore.Key;

/**
 *
 */
@Api(name = "interviewEndpoint", version = "v1")
public class InterviewV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(InterviewV1EndPoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public List<InterviewV1Dto> getInterviews(
            @Named("selectionKey") String selectionKey) {
        List<InterviewV1Dto> resultList = new ArrayList<InterviewV1Dto>();
        for (Interview interview : InterviewService
            .getInterviewListBySelectionKey(Datastore.stringToKey(selectionKey))) {
            InterviewV1Dto dto = new InterviewV1Dto();
            dto.setStartDate(interview.getStartDate().getTime());
            dto.setDuration(interview.getDuration());
            dto.setAtmosphere(interview.getAtmosphere());

            switch (interview.getCategory()) {
            case INDIVIDUAL:
                dto.setCategory(0);
                break;
            case GROUP:
                dto.setCategory(1);
                break;
            case GROUP_DISCUSSION:
                dto.setCategory(2);
                break;
            default:
                dto.setCategory(0);
            }

            resultList.add(dto);
        }
        return resultList;
    }

    public ResultV1Dto insertInterview(@Named("userKey") String userKey,
            @Named("selectionKey") String selectionKey,
            @Named("startTime") Long startTime) {
        ResultV1Dto result = new ResultV1Dto();
        User user = UserService.getUserByKey(Datastore.stringToKey(userKey));
        Selection Selection =
            SelectionService.getSelection(Datastore.stringToKey(selectionKey));
        try {
            if (user == null) {
                logger.warning("user not found");
                result.setResult(FAIL);
            } else if (Selection == null) {
                logger.warning("company not found");
                result.setResult(FAIL);
            } else {
                InterviewService.createInterview(user, Selection, new Date(
                    startTime));
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }

    public ResultV1Dto updateInterview(
            @Named("interviewKey") String interviewKey,
            @Named("duration") int duration,
            @Named("atmosphere") int atmosphere,
            @Named("category") int categoryValue,
            StringListContainer questionKeyListContainer) {
        ResultV1Dto result = new ResultV1Dto();
        Interview interview =
            InterviewService.getInterview(Datastore.stringToKey(interviewKey));
        try {
            if (interview == null) {
                logger.warning("user not found");
                result.setResult(FAIL);
            } else {
                Category category;
                switch (categoryValue) {
                case 0:
                    category = Category.INDIVIDUAL;
                    break;
                case 1:
                    category = Category.GROUP;
                    break;
                case 2:
                    category = Category.GROUP_DISCUSSION;
                    break;
                default:
                    category = Category.INDIVIDUAL;
                }

                InterviewService.updateInterview(
                    interview,
                    duration,
                    atmosphere,
                    category);
                List<Key> questionKeyList = new ArrayList<Key>();
                for (String questionKey : questionKeyListContainer.getList()) {
                    questionKeyList.add(Datastore.stringToKey(questionKey));
                }
                List<Question> questionList =
                    QuestionService.getQuestionListByKeyList(questionKeyList);
                for (Question question : questionList) {
                    InterviewQuestionMapService.createInterviewQuestionMap(
                        question,
                        interview);
                }
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }
}
