package jp.recruit.hps.movie.server.controller.system;

import java.io.IOException;

import jp.recruit.hps.movie.server.model.Register;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.RegisterService;
import jp.recruit.hps.movie.server.service.UserService;
import jp.recruit.hps.movie.server.utils.MailUtils;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.appengine.api.datastore.Key;

public class TempRegisterController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Key userKey = asKey("userKey");
        User user = UserService.getUserByKey(userKey);
        Register register;
        try {
            register = RegisterService.createRegister(user);
        } catch (InterruptedException e) {
            throw new IOException();
        }
        MailUtils.sendMail(user.getEmail(), register.getKey());
        return null;

    }
}
