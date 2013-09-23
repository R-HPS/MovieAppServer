package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.CompanyV1Dto;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.InterviewGroup;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "interviewGroupEndpoint", version = "v1")
public class InterviewGroupV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(InterviewGroupV1EndPoint.class.getName());

    public List<CompanyV1Dto> getInterviewGroups(@Named("userKey") String userKey) {
        List<CompanyV1Dto> resultList = new ArrayList<CompanyV1Dto>();
        try {
            List<Interview> interviewList =
                InterviewService.getInterviewListByUserKey(Datastore
                    .stringToKey(userKey));
            for (Interview interview : interviewList) {
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(interview
                    .getInterviewGroupRef()
                    .getKey()));
                InterviewGroup ig = interview.getInterviewGroupRef().getModel();
                dto.setName(ig.getCompanyRef().getModel().getName());
                dto.setPhase(ig.getPhase());
                resultList.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return resultList;
    }

    public List<CompanyV1Dto> searchCompany(@Named("keyword") String keyword) {
        List<CompanyV1Dto> result = new ArrayList<CompanyV1Dto>();
        try {
            List<Company> companyList = CompanyService.getCompanyList(keyword);
            for (Company company : companyList) {
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(company.getKey()));
                dto.setName(company.getName());
                result.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return result;
    }

}
