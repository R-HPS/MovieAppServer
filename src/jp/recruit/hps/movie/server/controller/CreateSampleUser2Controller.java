package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.model.University;
import jp.recruit.hps.movie.server.service.UniversityService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUser2Controller extends Controller {

    @Override
    public Navigation run() throws Exception {
        int i;
        for (i = 0; i < 5; i++) {
            String email = "test" + i + "@hoge.ac.jp";
            String password = "";
            University university =
                UniversityService.getUniversityByDomain("titech.ac.jp");
            if (UserService.getUserCount(email) == 0) {
                UserService.createUser(university, email, password);
            }
        }
        return null;
    }
}
