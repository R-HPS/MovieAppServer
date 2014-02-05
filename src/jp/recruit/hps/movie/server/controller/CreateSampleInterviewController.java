package jp.recruit.hps.movie.server.controller;

import java.util.Date;
import java.util.Random;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewService;
import jp.recruit.hps.movie.server.service.QuestionService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleInterviewController extends Controller {

    private static int ONE_HOUR = 3600 * 1000;

    @Override
    public Navigation run() throws Exception {
        Random rand = new Random(2312321313l);
        String[] questions =
            {
                "あなたが学生時代に頑張ったことは？",
                "あなたの長所と短所は？",
                "どうして弊社を志望したのですか？",
                "あなたの強みは？",
                "あなたを採用するメリットは？",
                "入社したらどんな仕事がしたいですか？",
                "自分を一言でいうと？" };
        User[] users =
            {
                UserService.getUserByEmail("test@test.titech.ac.jp"),
                UserService.getUserByEmail("test@test.u-tokyo.ac.jp"),
                UserService.getUserByEmail("test@test.keio.jp"),
                UserService.getUserByEmail("test@test.tsukuba.ac.jp"),
                UserService.getUserByEmail("test@test.meiji.ac.jp"),
                UserService.getUserByEmail("test@test.chuo-u.ac.jp") };
        Company company1 = CompanyService.getCompanyByName("株式会社リクルートホールディングス");
        Company company2 = CompanyService.getCompanyByName("サイバーエージェント");
        Company company3 = CompanyService.getCompanyByName("博報堂");

        int count = 0;
        for (User user : users) {
            if (user != null
                && InterviewService.getInterviewCount(
                    user.getKey(),
                    company1.getKey()) == 0) {
                Date startDate = new Date();
                startDate.setTime(1380326400000l + count++ * ONE_HOUR);
                int duration = 30;
                int atmosphere = 0;
                Category category = Category.INDIVIDUAL;
                InterviewService.createInterview(
                    user,
                    company1,
                    startDate,
                    duration,
                    atmosphere,
                    category);
                for (int i = 0; i < questions.length; i++) {
                    int a = rand.nextInt() % questions.length;
                    if (i < (a < 0 ? a + questions.length : a)) {
                        QuestionService.createQuestion(
                            user,
                            company1,
                            questions[i]);
                    }
                }
            }
            if (user != null
                && InterviewService.getInterviewCount(
                    user.getKey(),
                    company2.getKey()) == 0) {
                Date startDate = new Date();
                startDate.setTime(1380326400000l + (1 + count++ * ONE_HOUR));
                int duration = 20;
                int atmosphere = 1;
                Category category = Category.GROUP;
                InterviewService.createInterview(
                    user,
                    company2,
                    startDate,
                    duration,
                    atmosphere,
                    category);
                for (int i = 0; i < questions.length; i++) {
                    int a = rand.nextInt() % questions.length;
                    if (i < (a < 0 ? a + questions.length : a)) {
                        QuestionService.createQuestion(
                            user,
                            company2,
                            questions[i]);
                    }
                }
            }
            if (user != null
                && InterviewService.getInterviewCount(
                    user.getKey(),
                    company3.getKey()) == 0) {
                Date startDate = new Date();
                startDate.setTime(1380326400000l + (2 + count++ * ONE_HOUR));
                int duration = 50;
                int atmosphere = 2;
                Category category = Category.GROUP_DISCUSSION;
                InterviewService.createInterview(
                    user,
                    company3,
                    startDate,
                    duration,
                    atmosphere,
                    category);
                for (int i = 0; i < questions.length; i++) {
                    int a = rand.nextInt() % questions.length;
                    if (i < (a < 0 ? a + questions.length : a)) {
                        QuestionService.createQuestion(
                            user,
                            company3,
                            questions[i]);
                    }
                }
            }
        }
        return null;
    }
}
