package jp.recruit.hps.movie.server.controller;

import java.util.Date;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.University;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.SelectionService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.UniversityService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUserController extends Controller {

    private static int ONE_HOUR = 3600 * 1000;

    @Override
    public Navigation run() throws Exception {
        String email = "test@test.com";
        String password = "aaaaaa";
        University university = UniversityService.getUniversityByDomain("titech.ac.jp");
        if (UserService.getUserCount(email) == 0) {
            UserService.createUser(university, email, password);
        }
        User user = UserService.getUserByEmail(email);
        Company company1 = CompanyService.getCompanyByName("リクルートホールディングス");
        Selection Selection1 =
            SelectionService.getSelectionByCompanyKeyAndPhase(
                company1.getKey(),
                "1次面接");
        Company company2 = CompanyService.getCompanyByName("三菱商事");
        Selection Selection2 =
            SelectionService.getSelectionByCompanyKeyAndPhase(
                company2.getKey(),
                "1次面接");
        Company company3 = CompanyService.getCompanyByName("フジテレビ");
        Selection Selection3 =
            SelectionService.getSelectionByCompanyKeyAndPhase(
                company3.getKey(),
                "1次面接");
        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + ONE_HOUR);
        Date endDate = new Date();
        endDate.setTime(startDate.getTime() + ONE_HOUR);
        if (InterviewService
            .getInterviewCount(user.getKey(), company1.getKey()) == 0) {
            InterviewService.createInterview(user, Selection1, startDate);
        }
        if (InterviewService
            .getInterviewCount(user.getKey(), company2.getKey()) == 0) {
            InterviewService.createInterview(user, Selection2, startDate);
        }
        if (InterviewService
            .getInterviewCount(user.getKey(), company3.getKey()) == 0) {
            InterviewService.createInterview(user, Selection3, startDate);
        }
        return null;
    }
}
