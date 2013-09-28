package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.SelectionService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleCompanyController extends Controller {

    @Override
    public Navigation run() throws Exception {

        String[] names =
            {
                "株式会社リクルートキャリア",
                "株式会社リクルートジョブズ",
                "株式会社リクルート住まいカンパニー",
                "株式会社リクルートライフスタイル" };

        for (String name : names) {
            Company company;
            if (CompanyService.getCompanyCount(name) == 0) {
                company = CompanyService.createCompany(name);
            } else {
                company = CompanyService.getCompanyByName(name);
            }
            if (SelectionService.getSelectionCountByCompanyKey(company
                .getKey()) == 0) {
                for (int i = 1; i < 6; i++) {
                    SelectionService.createSelection(company, "一般採用", i
                        + "次選考");
                }
            }
            
        }
        return null;
    }
}
