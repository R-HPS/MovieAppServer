package jp.recruit.hps.movie.server.service;

import java.util.List;

import jp.recruit.hps.movie.server.meta.InterviewQuestionMapMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.InterviewQuestionMap;
import jp.recruit.hps.movie.server.model.Question;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewQuestionMapService {
    private static InterviewQuestionMapMeta meta = InterviewQuestionMapMeta
        .get();

    public static InterviewQuestionMap createInterviewQuestionMap(
            Company company, Question question, Interview interview) {
        InterviewQuestionMap map = new InterviewQuestionMap();
        Key key = Datastore.allocateId(InterviewQuestionMap.class);
        map.setKey(key);
        map.getCompanyRef().setModel(company);
        map.getQuestionRef().setModel(question);
        map.getInterviewRef().setModel(interview);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(map);
        tx.commit();
        return map;
    }

    public static List<InterviewQuestionMap> getInterviewQuestionMapListByCompanyKey(
            Key companyKey) {
        return Datastore
            .query(meta)
            .filter(meta.companyRef.equal(companyKey))
            .asList();
    }
}
