package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.CompanyV1Dto;
import jp.recruit.hps.movie.server.api.dto.SelectionV1Dto;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.Read;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.ReadService;
import jp.recruit.hps.movie.server.service.SelectionService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.datastore.Key;

/**
 *
 */
@Api(name = "selectionEndpoint", version = "v1")
public class SelectionV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(SelectionV1EndPoint.class.getName());
    
    public List<SelectionV1Dto> getCompanySelections(
        @Named("companyKey") String companyKey) {
    List<SelectionV1Dto> resultList = new ArrayList<SelectionV1Dto>();
    try {
        List<Selection> selectionList =
            SelectionService.getSelectionListByCompanyKey(Datastore.stringToKey(companyKey));

        for (Selection selection : selectionList) {
            SelectionV1Dto dto = new SelectionV1Dto();
            dto.setKey(Datastore.keyToString(selection.getKey()));
            dto.setPhase(selection.getPhase());
            resultList.add(dto);
        }
    } catch (Exception e) {
        logger.warning(e.getMessage());
    }
    return resultList;
}

    public List<CompanyV1Dto> getSelections(
            @Named("userKey") String userKey) {
        List<CompanyV1Dto> resultList = new ArrayList<CompanyV1Dto>();
        try {
            List<Interview> interviewList =
                InterviewService.getInterviewListByUserKey(Datastore
                    .stringToKey(userKey));
            List<Read> readList =
                ReadService.getReadList(Datastore.stringToKey(userKey));
            List<Key> readSelectionKeyList = new ArrayList<Key>();
            for (Read read : readList) {
                readSelectionKeyList.add(read
                    .getSelectionRef()
                    .getKey());
            }

            for (Interview interview : interviewList) {
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(interview
                    .getSelectionRef()
                    .getKey()));
                Selection ig = interview.getSelectionRef().getModel();
                dto.setName(ig.getCompanyRef().getModel().getName());
                dto.setPhase(ig.getPhase());
                dto.setStartDate(interview.getStartDate().getTime());
                dto.setWasRead(readSelectionKeyList.contains(ig.getKey()));
                resultList.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return resultList;
    }
}
