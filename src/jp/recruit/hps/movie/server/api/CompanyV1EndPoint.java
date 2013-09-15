package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import org.slim3.datastore.Datastore;

import jp.recruit.hps.movie.server.api.dto.CompanyV1Dto;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.service.CompanyService;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "companyEndpoint", version = "v1")
public class CompanyV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(CompanyV1EndPoint.class.getName());

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
