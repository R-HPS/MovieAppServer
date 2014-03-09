package jp.recruit.hps.movie.server.api;

import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.LoginResultV1Dto;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

@Api(name = "registerEndpoint", version = "v1")
public class RegisterV1Endpoint {

    private static final Logger logger = Logger
        .getLogger(RegisterV1Endpoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;


    public LoginResultV1Dto register(@Named("email") String email,
            @Named("password") String password,
            @Named("passwordAgain") String passwordAgain) {

        LoginResultV1Dto result = new LoginResultV1Dto();
        try {
            result.setResult(SUCCESS);
            if (SUCCESS.equals(result.getResult())) {
                User user = UserService.createUser(email, password);
                result.setMail(user.getEmail());
                result.setId(user.getId());
                result.setKey(Datastore.keyToString(user.getKey()));
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return result;
    }

}
