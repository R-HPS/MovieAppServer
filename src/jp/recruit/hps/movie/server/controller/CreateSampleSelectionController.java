package jp.recruit.hps.movie.server.controller;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.service.CompanyService;
import jp.recruit.hps.movie.server.service.SelectionService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

public class CreateSampleSelectionController extends Controller {

    @Override
    public Navigation run() throws Exception {
        String sections[] = { "ネット領域職種", "エンジニアスペシャリスト" };
        Company company = CompanyService.getCompanyByName("リクルート");
        if (company == null) {
            company = CompanyService.getCompanyByName("株式会社リクルートホールディングス");
        } else {
            company.setName("株式会社リクルートホールディングス");
            Datastore.put(company);
        }
        for (String section : sections) {
            for (int i = 1; i < 6; i++) {
                SelectionService.createSelection(company, section, i + "次面接");
            }
        }

        return null;
    }
}
