package jp.recruit.hps.movie.server.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

public class MailUtils {

    public static void sendMail(String email, String key)
            throws UnsupportedEncodingException {
        Logger logger = Logger.getLogger(MailUtils.class.getName());

        MailService.Message msg = new MailService.Message();

        msg.setSender("aimluck.ippei.takahashi@gmail.com");
        msg.setTo(email);
        msg.setSubject("");
        msg.setTextBody(key);

        MailService mailservice = MailServiceFactory.getMailService();
        try {
            mailservice.send(msg);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
