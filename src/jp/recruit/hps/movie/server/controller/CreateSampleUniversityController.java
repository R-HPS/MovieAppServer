package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.model.University;
import jp.recruit.hps.movie.server.service.UniversityService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUniversityController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String domains[] =
            {
                "titech.ac.jp",
                "u-tokyo.ac.jp",
                "keio.jp",
                "tsukuba.ac.jp",
                "meiji.ac.jp",
                "chuo-u.ac.jp" };
        String names[] = { "東京工業大学", "東京大学", "慶應義塾大学", "筑波大学", "明治大学", "中央大学" };
        for (int i = 0; i < domains.length; i++) {
            University university;
            if (UniversityService.getUniversityCount(domains[i]) == 0) {
                university =
                    UniversityService.createUniversity(names[i], domains[i]);
            } else {
                university =
                    UniversityService.getUniversityByDomain(domains[i]);
            }
            String email = "test@test." + domains[i];
            if (UserService.getUserCount(email) == 0) {
                UserService.createUser(university, email, "aaaaaa");
            }
        }

        return null;
    }
}
