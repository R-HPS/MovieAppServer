package jp.recruit.hps.movie.server.controller.system;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class FileUploadController extends Controller {
    @Override
    public Navigation run() throws Exception {
        return forward("fileupload.jsp");
    }
}
