package jp.recruit.hps.movie.server.controller;

import java.util.Date;

import jp.recruit.hps.movie.server.model.Register;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.model.User.State;
import jp.recruit.hps.movie.server.service.RegisterService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class RegisterController extends Controller {

    @Override
    public Navigation run() throws Exception {
        try {
            Key registerKey = asKey("key");
            Register register = RegisterService.getRegister(registerKey);
            if (register != null) {
                if (register.getExpire().getTime() > new Date().getTime()) {
                    User user = register.getUserRef().getModel();
                    UserService.updateState(user, State.ACTIVE);
                }
                Datastore.delete(registerKey);
                return forward("register.jsp");
            }
        } catch (Exception e) {
        }
        return null;

    }
}
