package jp.recruit.hps.movie.server.api;


import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.LoginResultV1Dto;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.UserService;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "loginEndpoint", version = "v1")
public class LoginV1Endpoint {
    private final static Logger logger = Logger.getLogger(
        LoginV1Endpoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public LoginResultV1Dto login(
            @Named("email") String email,
            @Named("password") String password) {

        LoginResultV1Dto result = new LoginResultV1Dto();
        try {
            if (email == null || password == null) {
                result.setResult(FAIL);
            } else {
                User user =
                    UserService.getUserByEmailAndPassword(email, password);
                if (user == null) {
                    result.setResult(FAIL);
                } else {
                    result.setResult(SUCCESS);
                    result.setMail(user.getEmail());
                    result.setId(user.getId());
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.setResult(FAIL);
        }

        return result;
    }
}
