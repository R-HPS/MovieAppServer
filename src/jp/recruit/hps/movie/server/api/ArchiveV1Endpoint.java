package jp.recruit.hps.movie.server.api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.ArchiveV1Dto;
import jp.recruit.hps.movie.server.model.Archive;
import jp.recruit.hps.movie.server.service.ArchiveService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "archiveEndpoint", version = "v1")
public class ArchiveV1Endpoint {
    public List<ArchiveV1Dto> getArchives(@Named("companyKey") String companyKey) {
        List<Archive> archiveList =
            ArchiveService.getArchiveListByCompanyKey(Datastore
                .stringToKey(companyKey));
        List<ArchiveV1Dto> resultList = new ArrayList<ArchiveV1Dto>();
        for(Archive archive : archiveList) {
            ArchiveV1Dto dto = new ArchiveV1Dto();
            dto.setType(archive.getType());
            dto.setYear(archive.getYear());
            dto.setBody(archive.getBody().getValue());
            resultList.add(dto);
        }

        return resultList;
    }
}
