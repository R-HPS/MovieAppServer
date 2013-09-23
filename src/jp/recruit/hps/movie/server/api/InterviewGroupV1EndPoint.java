package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.CompanyV1Dto;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.InterviewGroup;
import jp.recruit.hps.movie.server.model.Read;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.ReadService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.datastore.Key;

/**
 *
 */
@Api(name = "interviewGroupEndpoint", version = "v1")
public class InterviewGroupV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(InterviewGroupV1EndPoint.class.getName());

    public List<CompanyV1Dto> getInterviewGroups(
            @Named("userKey") String userKey) {
        List<CompanyV1Dto> resultList = new ArrayList<CompanyV1Dto>();
        try {
            List<Interview> interviewList =
                InterviewService.getInterviewListByUserKey(Datastore
                    .stringToKey(userKey));
            List<Read> readList =
                ReadService.getReadList(Datastore.stringToKey(userKey));
            List<Key> readInterviewGroupKeyList = new ArrayList<Key>();
            for (Read read : readList) {
                readInterviewGroupKeyList.add(read
                    .getInterviewGroupRef()
                    .getKey());
            }

            for (Interview interview : interviewList) {
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(interview
                    .getInterviewGroupRef()
                    .getKey()));
                InterviewGroup ig = interview.getInterviewGroupRef().getModel();
                dto.setName(ig.getCompanyRef().getModel().getName());
                dto.setPhase(ig.getPhase());
                dto.setWasRead(readInterviewGroupKeyList.contains(ig.getKey()));
                resultList.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return resultList;
    }
}
