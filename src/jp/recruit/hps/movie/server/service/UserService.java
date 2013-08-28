package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.UserMeta;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.utils.Encrypter;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class UserService {
    private static UserMeta meta = UserMeta.get();

    private static User createUser(Map<String, Object> input) {
        User user = new User();
        Key key = Datastore.allocateId(User.class);
        BeanUtil.copy(input, user);
        user.setKey(key);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(user);
        tx.commit();
        return user;
    }

    public static User createUser(String userId, String email, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("email", email);
        map.put("password", Encrypter.getHash(password));
        return createUser(map);
    }

    public static User getUserByEmail(String email) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.email.equal(email))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static User getUserByEmailAndPassword(String email, String password) {
        try {
            return Datastore
                .query(meta)
                .filter(
                    meta.email.equal(email),
                    meta.password.equal(Encrypter.getHash(password)))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
