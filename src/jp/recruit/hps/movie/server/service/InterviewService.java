package jp.recruit.hps.movie.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.InterviewMeta;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.Interview.Atmosphere;
import jp.recruit.hps.movie.server.model.Interview.Category;
import jp.recruit.hps.movie.server.model.InterviewGroup;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewService {
    private static InterviewMeta meta = InterviewMeta.get();

    public static Interview createInterview(Map<String, Object> input,
            User user, InterviewGroup interviewGroup) {
        Interview interview = new Interview();
        Key key = Datastore.allocateId(Interview.class);
        BeanUtil.copy(input, interview);
        interview.setKey(key);
        interview.getUserRef().setModel(user);
        interview.getInterviewGroupRef().setModel(interviewGroup);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(interview);
        tx.commit();
        return interview;
    }

    public static Interview createInterview(User user, InterviewGroup interviewGroup,
            Date startDate, Date endDate, String question, int atmosphere,
            Category category) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("question", question);
        map.put("atomosphere", atmosphere);
        map.put("category", category);
        return createInterview(map, user, interviewGroup);
    }

    public static Interview createInterview(User user, InterviewGroup interviewGroup,
            Date startDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        return createInterview(map, user, interviewGroup);
    }
    
    public static void updateInterview(Interview interview,
            Date startDate, Date endDate, String question, Atmosphere atmosphere,
            Category category) {
        interview.setStartDate(startDate);
        interview.setEndDate(endDate);
        interview.setQuestion(question);
        interview.setAtmosphere(atmosphere);
        interview.setCategory(category);
    }

    public static List<Interview> getInterviewListByInterviewGroupKey(Key key) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.interviewGroupRef.equal(key))
                .sort(meta.startDate.asc)
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
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getInterviewCount(Key userKey, Key interviewGroupKey) {
        return Datastore
            .query(meta)
            .filter(
                meta.userRef.equal(userKey),
                meta.interviewGroupRef.equal(interviewGroupKey))
            .count();
    }
}
