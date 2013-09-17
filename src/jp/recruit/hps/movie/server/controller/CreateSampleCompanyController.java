package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.service.CompanyService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class CreateSampleCompanyController extends Controller {

    @Override
    public Navigation run() throws Exception {

        String[] names =
            { "リクルートホールディングス", "リクルートキャリア", "リクルートジョブズ", "三菱商事", "フジテレビ" };

        for (String name : names) {
            if (CompanyService.getCompanyByName(name) == null) {
                CompanyService.createCompany(name);
            }
        }
        return null;
    }
}
