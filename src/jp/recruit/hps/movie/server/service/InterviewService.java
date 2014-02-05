package jp.recruit.hps.movie.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.InterviewMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewService {
    private static InterviewMeta meta = InterviewMeta.get();

    public static Interview createInterview(Map<String, Object> input,
            User user, Company company) {
        if (getInterviewCount(user.getKey(), company.getKey()) > 0) {
            return null;
        }
        Interview interview = new Interview();
        Key key = Datastore.allocateId(Interview.class);
        BeanUtil.copy(input, interview);
        interview.setKey(key);
        interview.getUserRef().setModel(user);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(interview);
        tx.commit();
        return interview;
    }

    public static Interview createInterview(User user, Company company,
            Date startDate, int duration, int atmosphere, Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("duration", duration);
        map.put("atmosphere", atmosphere);
        map.put("category", category);
        return createInterview(map, user, company);
    }

    public static Interview createInterview(User user, Company company,
            Date startDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        return createInterview(map, user, company);
    }

    public static void updateInterview(Interview interview, int duration,
            int atmosphere, Category category) {
        interview.setDuration(duration);
        interview.setAtmosphere(atmosphere);
        interview.setCategory(category);
        Datastore.put(interview);
    }

    public static Interview getInterview(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }

    }

    public static Interview getInterviewByCompanyKeyAndUserKey(
            Key companyKey, Key userKey) {
        try {
            return Datastore
                .query(meta)
                .filter(
                    meta.companyRef.equal(companyKey),
                    meta.userRef.equal(userKey))
                .asList()
                .get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Interview> getInterviewListByCompanyKey(Key key) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.companyRef.equal(key))
                .filterInMemory(meta.startDate.greaterThanOrEqual(new Date()))
                .sortInMemory(meta.startDate.asc)
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Interview> getInterviewListByUserKey(Key key) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.userRef.equal(key))
                .filterInMemory(meta.startDate.greaterThanOrEqual(new Date()))
                .sortInMemory(meta.startDate.asc)
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInterviewCount(Key userKey, Key companyKey) {
        return Datastore
            .query(meta)
            .filter(
                meta.userRef.equal(userKey),
                meta.companyRef.equal(companyKey))
            .count();
    }
    
    public static int getInterviewCountByCompanyKey(Key companyKey) {
        return Datastore
            .query(meta)
            .filter(
                meta.companyRef.equal(companyKey))
            .count();
    }
}
