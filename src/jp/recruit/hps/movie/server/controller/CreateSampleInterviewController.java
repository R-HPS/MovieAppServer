package jp.recruit.hps.movie.server.controller;

import java.util.Date;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.SelectionService;
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
        Selection Selection =
                SelectionService.getSelectionByCompanyKeyAndPhase(
                    company.getKey(),
                    "1次面接");
        for (i = 0; i < 5; i++) {
            String email = "test" + i + "@hoge.ac.jp";
            User user = UserService.getUserByEmail(email);
            if (user != null
                && InterviewService.getInterviewCount(
                    user.getKey(),
                    Selection.getKey()) == 0) {
                Date startDate = new Date();
                startDate.setTime(startDate.getTime() + i * ONE_HOUR);
                int duration = 30;
                String question = questions[i];
                int atmosphere = 0;
                Category category = Category.INDIVIDUAL;
                InterviewService.createInterview(
                    user,
                    Selection,
                    startDate,
                    duration,
                    question,
                    atmosphere,
                    category);
            }
        }
        return null;
    }
}
