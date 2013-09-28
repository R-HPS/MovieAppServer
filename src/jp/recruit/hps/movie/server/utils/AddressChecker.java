package jp.recruit.hps.movie.server.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressChecker {
    public static String check(String address) {
        String ptnStr =
            "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+"
                + "[\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+]*"
                + "@"
                + "[[a-zA-Z0-9][a-zA-Z0-9\\-]*\\.]*?([[a-zA-Z0-9\\-]+\\.ac|keio]\\.jp)$";
        Pattern ptn = Pattern.compile(ptnStr);
        Matcher mc = ptn.matcher(address);
        if (mc.matches()) {
            return mc.group(1);
        } else {
            return null;
        }
    }
}
