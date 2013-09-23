package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.InterviewGroupService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleCompanyController extends Controller {

    @Override
    public Navigation run() throws Exception {

        String[] names =
            { "リクルートホールディングス", "リクルートキャリア", "リクルートジョブズ", "三菱商事", "フジテレビ" };

        for (String name : names) {
            Company company;
            if (CompanyService.getCompanyCount(name) == 0) {
                company = CompanyService.createCompany(name);
            } else {
                company = CompanyService.getCompanyByName(name);
            }
            if (company != null) {
                for (int i = 1; i < 4; i++) {
                    InterviewGroupService.createInterviewGroup(company, i + "次面接");
                }
            }
        }
        return null;
    }
}
