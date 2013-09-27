package jp.recruit.hps.movie.server.service;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.logging.Logger;

import jp.recruit.hps.movie.server.meta.RegisterMeta;
import jp.recruit.hps.movie.server.model.Register;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class RegisterService {
    private static RegisterMeta meta = RegisterMeta.get();

    private static long ONE_DAY = 24 * 3600 * 1000;
    
    private static Logger logger = Logger.getLogger(RegisterService.class.getName());

    public static Register createRegister(User user) throws InterruptedException {
        Register register = new Register();
        Key key = Datastore.allocateId(Register.class);
        register.setKey(key);
        Date expire = new Date();
        expire.setTime(expire.getTime() + ONE_DAY);
        register.setExpire(expire);
        register.getUserRef().setModel(user);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(register);
        try {
            tx.commit();
        } catch (ConcurrentModificationException e) {
            logger.warning(e.getMessage());
        }
        return register;
    }

    public static Register getRegister(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
