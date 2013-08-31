package jp.recruit.hps.movie.server.api;

import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.MovieService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "movieEndpoint", version = "v1")
public class MovieV1EndPoint {
    private final static Logger logger = Logger.getLogger(MovieV1EndPoint.class
        .getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public ResultV1Dto createMovie(@Named("userKey") String userKey,
            @Named("fileName") String fileName) {
        ResultV1Dto result = new ResultV1Dto();

        User user = UserService.getUserByKey(Datastore.stringToKey(userKey));
        try {
            if (user == null) {
                logger.warning("user not found");
                result.setResult(FAIL);
            } else if (MovieService.createMovie(fileName, new Date(), user) == null) {
                logger.warning("movie not created");
                result.setResult(FAIL);
            } else {
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }
}
