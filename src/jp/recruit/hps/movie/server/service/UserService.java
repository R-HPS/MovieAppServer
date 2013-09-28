package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.UserMeta;
import jp.recruit.hps.movie.server.model.University;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.model.User.State;
import jp.recruit.hps.movie.server.utils.Encrypter;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class UserService {
    private static UserMeta meta = UserMeta.get();

    private static User createUser(Map<String, Object> input, University university) {
        User user = new User();
        Key key = Datastore.allocateId(User.class);
        BeanUtil.copy(input, user);
        user.setKey(key);
        user.setId(key.getId());
        user.getUniversityRef().setModel(university);
        user.setPoint(3);
        user.setState(State.PAUSE);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(user);
        tx.commit();
        return user;
    }

    public static User createUser(University university, String email, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);
        map.put("password", Encrypter.getHash(password));
        return createUser(map,university);
    }

    public static User getUserByKey(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
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

    public static int getUserCount(String email) {
        return Datastore.query(meta).filter(meta.email.equal(email)).count();
    }

    public static void updateState(User user, State newState) {
        user.setState(newState);
        Datastore.put(user);
    }

    public static List<User> getUserListByEmailList(List<String> emailList) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.email.in(emailList))
                .sort(meta.id.asc)
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void addPoint(User user) {
        user.setPoint(user.getPoint() + 1);
        Datastore.put(user.getKey());
    }
    
    public static void usePoint(User user) {
        user.setPoint(user.getPoint() - 1);
        Datastore.put(user.getKey());
    }
}
