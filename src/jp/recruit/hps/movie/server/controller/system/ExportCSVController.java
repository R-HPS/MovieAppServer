package jp.recruit.hps.movie.server.controller.system;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.service.ArchiveService;
import jp.recruit.hps.movie.server.service.CompanyService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

public class ExportCSVController extends Controller {

    @Override
    public Navigation run() throws Exception {
        FileItem formFile = requestScope("formFile");
        InputStream is = new ByteArrayInputStream(formFile.getData());
        String CL = System.getProperty("line.separator");

        BufferedReader br =
            new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String line = null;

        while ((line = br.readLine()) != null) {
            String[] datas = line.split(";");
            String name;
            String type;
            if (datas[0].contains("（")) {
                name = datas[0].substring(0, datas[0].indexOf("（"));
                type =
                    datas[0].substring(
                        datas[0].indexOf("（") + 1,
                        datas[0].length() - 1);
            } else {
                name = datas[0];
                type = "";
            }
            int year = Integer.valueOf("20" + datas[1].substring(0, 1));
            String body = datas[2];
            body = body.replace("%%", CL);
            Company company;
            if (CompanyService.getCompanyCount(name) > 0) {
                company = CompanyService.getCompanyByName(name);
            } else {
                company = CompanyService.createCompany(name);
            }
            if (ArchiveService.getArchiveCountByNameAndYear(name, year) == 0) {
                ArchiveService.createArchive(company, name, type, year, body);
            }
        }
        return null;

    }
}
