package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.InterviewV1Dto;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.SelectionService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

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
            @Named("SelectionKey") String SelectionKey) {
        List<InterviewV1Dto> resultList = new ArrayList<InterviewV1Dto>();
        for (Interview interview : InterviewService
            .getInterviewListBySelectionKey(Datastore
                .stringToKey(SelectionKey))) {
            InterviewV1Dto dto = new InterviewV1Dto();
            dto.setStartDate(interview.getStartDate().getTime());
            dto.setDuration(interview.getDuration());
            dto.setAtmosphere(interview.getAtmosphere());
            dto.setCategory(interview.getCategory());
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
}
