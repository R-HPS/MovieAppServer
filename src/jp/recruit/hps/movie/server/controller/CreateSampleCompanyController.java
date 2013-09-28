package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.service.CompanyService;

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
            if (CompanyService.getCompanyCount(name) == 0) {
                CompanyService.createCompany(name);

            }
        }
        return null;
    }
}
