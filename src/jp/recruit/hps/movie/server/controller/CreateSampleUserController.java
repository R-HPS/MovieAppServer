package jp.recruit.hps.movie.server.controller;

import java.util.Date;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleUserController extends Controller {

    private static int ONE_HOUR = 3600 * 1000;

    @Override
    public Navigation run() throws Exception {
        String email = "test@test.com";
        String lastName = "ポニー";
        String firstName = "村山";
        String password = "";
        if (UserService.getUserCount(email) == 0) {
            UserService.createUser(email, password, firstName, lastName);
        }
        User user = UserService.getUserByEmail(email);
        Company company1 = CompanyService.getCompanyByName("リクルートホールディングス");
        Company company2 = CompanyService.getCompanyByName("三菱商事");
        Company company3 = CompanyService.getCompanyByName("フジテレビ");
        Date startDate = new Date();
        startDate.setTime(startDate.getTime() + ONE_HOUR);
        Date endDate = new Date();
        endDate.setTime(startDate.getTime() + ONE_HOUR);
        String question = "";
        if (InterviewService
            .getInterviewCount(user.getKey(), company1.getKey()) == 0) {
            InterviewService.createInterview(
                user,
                company1,
                startDate,
                endDate,
                question);
        }
        if (InterviewService
            .getInterviewCount(user.getKey(), company2.getKey()) == 0) {
            InterviewService.createInterview(
                user,
                company2,
                startDate,
                endDate,
                question);
        }
        if (InterviewService
            .getInterviewCount(user.getKey(), company3.getKey()) == 0) {
            InterviewService.createInterview(
                user,
                company3,
                startDate,
                endDate,
                question);
        }
        return null;
    }
}
