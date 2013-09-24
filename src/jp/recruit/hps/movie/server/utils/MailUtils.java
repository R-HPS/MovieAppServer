package jp.recruit.hps.movie.server.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailServiceFactory;

public class MailUtils {
    private static String SENDER_MAIL = "aimluck.ippei.takahashi@gmail.com";

    public static void sendMail(String email, Key key)
            throws UnsupportedEncodingException {
        Logger logger = Logger.getLogger(MailUtils.class.getName());

        MailService.Message msg = new MailService.Message();

        String CL = System.getProperty("line.separator");

        msg.setSender(SENDER_MAIL);
        msg.setTo(email);
        msg.setSubject("[バトン]　ご登録ありがとうございます");
        msg.setTextBody("バトンへのご登録ありがとうございます。"
            + CL
            + "24時間以内に以下のURLから、登録を完了してください。"
            + CL
            + "https://hps-movie.appspot.com/register?key="
            + Datastore.keyToString(key));

        MailService mailservice = MailServiceFactory.getMailService();
        try {
            mailservice.send(msg);
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
