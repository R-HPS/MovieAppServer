package jp.recruit.hps.movie.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.InterviewMeta;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.Selection;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewService {
    private static InterviewMeta meta = InterviewMeta.get();

    public static Interview createInterview(Map<String, Object> input,
            User user, Selection selection) {
        Interview interview = new Interview();
        Key key = Datastore.allocateId(Interview.class);
        BeanUtil.copy(input, interview);
        interview.setKey(key);
        interview.getUserRef().setModel(user);
        interview.getSelectionRef().setModel(selection);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(interview);
        tx.commit();
        return interview;
    }

    public static Interview createInterview(User user, Selection selection,
            Date startDate, int duration, String question, int atmosphere,
            Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("duration", duration);
        map.put("question", question);
        map.put("atmosphere", atmosphere);
        map.put("category", category);
        return createInterview(map, user, selection);
    }

    public static Interview createInterview(User user, Selection selection,
            Date startDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        return createInterview(map, user, selection);
    }

    public static void updateInterview(Interview interview, int duration,
            int atmosphere, Category category) {
        interview.setDuration(duration);
        interview.setAtmosphere(atmosphere);
        interview.setCategory(category);
    }

    public static Interview getInterview(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }

    }
    
    public static Interview getInterviewBySelectionKeyAndUserKey(Key selectionKey, Key userKey) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.selectionRef.equal(selectionKey), meta.userRef.equal(userKey))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Interview> getInterviewListBySelectionKey(Key key) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.selectionRef.equal(key))
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

    public static int getInterviewCount(Key userKey, Key selectionKey) {
        return Datastore
            .query(meta)
            .filter(
                meta.userRef.equal(userKey),
                meta.selectionRef.equal(selectionKey))
            .count();
    }
}
