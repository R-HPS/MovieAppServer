package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.UniversityMeta;
import jp.recruit.hps.movie.server.model.University;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class UniversityService {
    private static UniversityMeta meta = UniversityMeta.get();

    private static University createUniversity(Map<String, Object> input) {
        University university = new University();
        Key key = Datastore.allocateId(University.class);
        BeanUtil.copy(input, university);
        university.setKey(key);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(university);
        tx.commit();
        return university;
    }

    public static University createUniversity(String name, String domain) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("domain", domain);
        return createUniversity(map);
    }

    public static University getUniversity(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static University getUniversityByDomain(String domain) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.domain.equal(domain))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getUniversityCount(String domain) {
        return Datastore.query(meta).filter(meta.domain.equal(domain)).count();
    }

}
