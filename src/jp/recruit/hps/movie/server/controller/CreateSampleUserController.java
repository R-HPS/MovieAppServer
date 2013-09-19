package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUserController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String email = "test@test.com";
        String lastName = "ポニー";
        String firstName = "村山";
        String password = "";
        if (UserService.getUserCount(email) == 0) {
            UserService.createUser(email, password, firstName, lastName);
        }
        return null;
    }
}
