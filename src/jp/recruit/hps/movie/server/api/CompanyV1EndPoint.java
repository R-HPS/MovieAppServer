package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.CompanyV1Dto;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "companyEndpoint", version = "v1")
public class CompanyV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(CompanyV1EndPoint.class.getName());
    private final static int NEW_INTERVIEW_LIMIT = 20;

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

    public List<CompanyV1Dto> getCompanyList(@Named("userKey") String userKey) {
        List<CompanyV1Dto> result = new ArrayList<CompanyV1Dto>();
        try {
            List<Interview> interviewList =
                InterviewService.getInterviewListByUserKey(Datastore
                    .stringToKey(userKey));
            for (Interview interview : interviewList) {
                Company company = interview.getCompanyRef().getModel();
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(company.getKey()));
                dto.setInterviewKey(Datastore.keyToString(interview.getKey()));
                dto.setName(company.getName());
                dto.setStartDate(interview.getStartDate().getTime());
                dto.setWasRead(interview.getIsRead());
                result.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return result;
    }
    
    public List<CompanyV1Dto> getNewCompanyList() {
        List<CompanyV1Dto> result = new ArrayList<CompanyV1Dto>();
        try {
            List<Interview> interviewList =
                InterviewService.getNewInterviewList(NEW_INTERVIEW_LIMIT);
            for (Interview interview : interviewList) {
                Company company = interview.getCompanyRef().getModel();
                CompanyV1Dto dto = new CompanyV1Dto();
                dto.setKey(Datastore.keyToString(company.getKey()));
                dto.setName(company.getName());
                dto.setStartDate(interview.getStartDate().getTime());
                dto.setWasRead(interview.getIsRead());
                result.add(dto);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return result;
    }

}
