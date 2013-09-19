package jp.recruit.hps.movie.server.controller;

import java.util.Date;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleInterviewController extends Controller {

    private static int ONE_HOUR = 3600 * 1000;
    
    @Override
    public Navigation run() throws Exception {

        String[] questions =
            {
                "あなたが学生時代に頑張ったことは？",
                "あなたの長所はなんですか？",
                "学生時代に頑張ったこと",
                "長所と短所",
                "長所について" };
        int i;
        Company company = CompanyService.getCompanyByName("リクルートホールディングス");
        for (i = 0; i < 5; i++) {
            String email = "test" + i + "@hoge.ac.jp";
            User user = UserService.getUserByEmail(email);
            if (user != null
                && InterviewService.getInterviewCount(
                    user.getKey(),
                    company.getKey()) == 0) {
                Date startDate = new Date();
                startDate.setTime(startDate.getTime() + i * ONE_HOUR);
                Date endDate = new Date();
                endDate.setTime(startDate.getTime() + ONE_HOUR);
                String question = questions[i];
                int atmosphere = 10 + (i % 2) * 5;
                Category category = Category.INDIVIDUAL;
                InterviewService.createInterview(
                    user,
                    company,
                    startDate,
                    endDate,
                    question,
                    atmosphere,
                    category);
            }
        }
        return null;
    }
}