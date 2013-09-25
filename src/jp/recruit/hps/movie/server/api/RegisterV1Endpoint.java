package jp.recruit.hps.movie.server.api;

import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.common.RegisterConstant;
import jp.recruit.hps.movie.server.api.dto.RegisterResultV1Dto;
import jp.recruit.hps.movie.server.model.Register;
import jp.recruit.hps.movie.server.model.University;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.RegisterService;
import jp.recruit.hps.movie.server.service.UniversityService;
import jp.recruit.hps.movie.server.service.UserService;
import jp.recruit.hps.movie.server.utils.AddressChecker;
import jp.recruit.hps.movie.server.utils.MailUtils;

import com.google.api.server.spi.config.Api;

@Api(name = "registerEndpoint", version = "v1")
public class RegisterV1Endpoint {

    private static final Logger logger = Logger
        .getLogger(RegisterV1Endpoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;
    private static final String NULL_EMAIL = RegisterConstant.NULL_EMAIL;
    private static final String NULL_PASS = RegisterConstant.NULL_PASS;
    private static final String NULL_PASSA = RegisterConstant.NULL_PASSA;
    private static final String INVALID_ADDRESS =
        RegisterConstant.INVALID_ADDRESS;
    private static final String EXISTING_ADDRESS =
        RegisterConstant.EXISTING_ADDRESS;
    private static final String SHORT_PASS = RegisterConstant.SHORT_PASS;
    private static final String DIFFERENT_PASS =
        RegisterConstant.DIFFERENT_PASS;
    private static final String UNEXPECTED_ERROR =
        RegisterConstant.UNEXPECTED_ERROR;
    private static final int PASS_LENGTH = RegisterConstant.PASS_LENGTH;

    public RegisterResultV1Dto register(@Named("email") String email,
            @Named("password") String password,
            @Named("passwordAgain") String passwordAgain) {

        RegisterResultV1Dto result = new RegisterResultV1Dto();
        try {
            result.setResult(SUCCESS);
            validateEmail(result, email);
            validatePassword(result, password, passwordAgain);
            if (result.getErrorList().size() > 0) {
                result.setResult(FAIL);
            } else {
                result.setResult(SUCCESS);
            }
            if (SUCCESS.equals(result.getResult())) {
                University university =
                    UniversityService.getUniversityByDomain(AddressChecker
                        .check(email));
                User user = UserService.createUser(university, email, password);
                Register register = RegisterService.createRegister(user);
                MailUtils.sendMail(email, register.getKey());
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.addError(UNEXPECTED_ERROR);
        }
        return result;
    }

    private void validateEmail(RegisterResultV1Dto result, String email) {
        if (email == null) {
            result.addError(NULL_EMAIL);
        } else if (AddressChecker.check(email) == null) {
            result.addError(INVALID_ADDRESS);
        } else if (UserService.getUserByEmail(email) != null) {
            result.addError(EXISTING_ADDRESS);
        }
    }

    private void validatePassword(RegisterResultV1Dto result, String password,
            String passwordAgain) {
        if (password == null) {
            result.addError(NULL_PASS);
        } else if (password.length() < PASS_LENGTH) {
            result.addError(SHORT_PASS);
        }
        if (passwordAgain == null) {
            result.addError(NULL_PASSA);
        } else if (!password.equals(passwordAgain)) {
            result.addError(DIFFERENT_PASS);
        }
    }
}
