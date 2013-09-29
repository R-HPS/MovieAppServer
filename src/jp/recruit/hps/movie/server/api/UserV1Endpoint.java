package jp.recruit.hps.movie.server.api;

import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.server.api.dto.PointV1Dto;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "userEndpoint", version = "v1")
public class UserV1Endpoint {
    Logger logger = Logger.getLogger(UserV1Endpoint.class.getName());

    public PointV1Dto login(@Named("userKey") String userKey) {
        User user = UserService.getUserByKey(Datastore.stringToKey(userKey));
        PointV1Dto result = new PointV1Dto();
        if (user != null) {
            result.setValue(user.getPoint());
        } else {
            result.setValue(-1);
        }

        return result;
    }
}
