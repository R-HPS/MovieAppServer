package jp.recruit.hps.movie.server.controller.system;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.recruit.hps.movie.server.service.CompanyService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

public class ExportCSVController extends Controller {

    @Override
    public Navigation run() throws Exception {
        FileItem formFile = requestScope("formFile");
        InputStream is = new ByteArrayInputStream(formFile.getData());

        BufferedReader br =
            new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String name = null;

        while ((name = br.readLine()) != null) {
            if (CompanyService.getCompanyCount(name) == 0) {
                CompanyService.createCompany(name);
            }
        }
        return null;

    }
}
