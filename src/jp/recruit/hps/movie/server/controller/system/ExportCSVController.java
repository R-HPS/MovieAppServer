package jp.recruit.hps.movie.server.controller.system;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class ExportCSVController extends Controller {

    @Override
    public Navigation run() throws Exception {
        FileItem formFile = requestScope("formFile");
        InputStream is = new ByteArrayInputStream(formFile.getData());

        BufferedReader br =
            new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String name = null;
        StringBuffer companyNames = new StringBuffer();
        int i = 0;
        while ((name = br.readLine()) != null) {
            if (i > 0) {
                companyNames.append(",");
            }
            i++;
            companyNames.append(name);
            if (i > 300) {
                TaskOptions taskOptions =
                    TaskOptions.Builder
                        .withUrl("/system/registerCompany")
                        .param("companyNames", companyNames.toString());
                Queue defaultQueue = QueueFactory.getDefaultQueue();
                defaultQueue.add(taskOptions);

                companyNames = new StringBuffer();
                i = 0;
            }
        }

        TaskOptions taskOptions =
            TaskOptions.Builder.withUrl("/system/registerCompany").param(
                "companyNames",
                companyNames.toString());
        Queue defaultQueue = QueueFactory.getDefaultQueue();
        defaultQueue.add(taskOptions);
        return null;

    }
}
