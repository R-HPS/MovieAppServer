package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.container.StringListContainer;
import jp.recruit.hps.movie.server.api.dto.InterviewV1Dto;
import jp.recruit.hps.movie.server.api.dto.QuestionWithCountV1Dto;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.InterviewQuestionMap;
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

    public InterviewV1Dto getInterviews(
            @Named("selectionKey") String selectionKey) {
        InterviewV1Dto result = new InterviewV1Dto();
        List<QuestionWithCountV1Dto> resultList =
            new ArrayList<QuestionWithCountV1Dto>();

        int interviewCount = 0;
        long durationSum = 0l;
        long atmosphereSum = 0l;

        int individualCount = 0;
        int groupCount = 0;
        int groupDiscussionCount = 0;

        for (Interview interview : InterviewService
            .getInterviewListBySelectionKey(Datastore.stringToKey(selectionKey))) {

            interviewCount++;
            durationSum += interview.getDuration();
            atmosphereSum += interview.getAtmosphere();

            switch (interview.getCategory()) {
            case INDIVIDUAL:
                individualCount++;
                break;
            case GROUP:
                groupCount++;
                break;
            case GROUP_DISCUSSION:
                groupDiscussionCount++;
                break;
            default:
            }
        }

        if (interviewCount == 0) {
            result.setAtmosphereAvg(0);
            result.setDurationAvg(0);
        } else {
            result.setAtmosphereAvg((double) atmosphereSum
                / (double) interviewCount);
            result.setDurationAvg((double) durationSum
                / (double) interviewCount);
        }

        if (individualCount > groupCount) {
            if (individualCount > groupDiscussionCount) {
                result.setCategory(0);
            } else {
                result.setCategory(2);
            }
        } else if (groupCount > groupDiscussionCount) {
            result.setCategory(1);
        } else {
            result.setCategory(2);
        }

        Map<Key, QuestionWithCountV1Dto> questionMap =
            new HashMap<Key, QuestionWithCountV1Dto>();
        List<Question> questionList =
            QuestionService.getQuestionListBySelectionKey(Datastore
                .stringToKey(selectionKey));

        for (Question question : questionList) {
            QuestionWithCountV1Dto dto = new QuestionWithCountV1Dto();
            dto.setKey(Datastore.keyToString(question.getKey()));
            dto.setName(question.getName());
            questionMap.put(question.getKey(), dto);
        }
        /* 質問数集計 */
        for (InterviewQuestionMap map : InterviewQuestionMapService
            .getInterviewQuestionMapBySelectionKey(Datastore
                .stringToKey(selectionKey))) {
            QuestionWithCountV1Dto dto =
                questionMap.get(map.getQuestionRef().getKey());
            dto.setCount(dto.getCount() + 1);
        }
        for (Question question : questionList) {
            QuestionWithCountV1Dto dto = questionMap.get(question.getKey());
            dto.setPercent((double) dto.getCount() / (double) interviewCount);
        }

        resultList.addAll(questionMap.values());
        result.setQuestionList(resultList);
        return result;
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
