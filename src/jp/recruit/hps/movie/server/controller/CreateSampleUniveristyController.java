package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.service.UniversityService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUniveristyController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String domain = "titech.ac.jp";
        String name = "東京工業大学";
        if (UniversityService.getUniversityCount(domain) == 0) {
            UniversityService.createUniversity(name, domain);
        }
        return null;
    }
}
