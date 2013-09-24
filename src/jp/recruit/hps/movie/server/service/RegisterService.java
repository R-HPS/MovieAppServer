package jp.recruit.hps.movie.server.service;

import java.util.Date;

import jp.recruit.hps.movie.server.meta.RegisterMeta;
import jp.recruit.hps.movie.server.model.Register;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class RegisterService {
    private static RegisterMeta meta = RegisterMeta.get();
    
    private static long ONE_DAY = 24 * 3600 * 1000;

    public static Register createRegister(User user) {
        Register register = new Register();
        Key key = Datastore.allocateId(Register.class);
        register.setKey(key);
        register.setExpire(new Date(new Date().getTime() + ONE_DAY));
        register.getUserRef().setModel(user);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(register);
        tx.commit();
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
