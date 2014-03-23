package jp.recruit.hps.movie.server.controller.system;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.QuestionService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class RegisterCompanyController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String names = asString("companyNames");
        String[] nameArray = names.split(",");
        for (String name: nameArray) {
            Company company = null;
            User user = UserService.getUserByEmail("d15dae12-19c6-4998-abbf-f8ab0986d812@test.ac.jp");
            if (CompanyService.getCompanyCount(name) == 0) {
                company = CompanyService.createCompany(name);
            } else {
                company = CompanyService.getCompanyByName(name);
            }
            if (company != null && user != null) {
                QuestionService.createQuestion(user, company, "あなたの強みを教えてください");
                QuestionService.createQuestion(user, company, "仕事で大切だと思うことは？");
            }
        }
        return null;

    }
}
