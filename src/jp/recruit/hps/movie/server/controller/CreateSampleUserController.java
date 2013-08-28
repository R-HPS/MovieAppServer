package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUserController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String userId = "test";
        String email = "test@test.com";
        String password = "";
        if (UserService.getUserByEmailAndPassword(email, password) == null) {
            UserService.createUser(userId, email, password);
        }
        return null;
    }
}
