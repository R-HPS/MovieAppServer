package jp.recruit.hps.movie.server.service;

import jp.recruit.hps.movie.server.meta.InterviewQuestionMapMeta;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.InterviewQuestionMap;
import jp.recruit.hps.movie.server.model.Question;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewQuestionMapService {
    private static InterviewQuestionMapMeta meta = InterviewQuestionMapMeta.get();

    public static InterviewQuestionMap createInterviewQuestionMap(Question question, Interview interview) {
        InterviewQuestionMap map = new InterviewQuestionMap();
        Key key = Datastore.allocateId(InterviewQuestionMap.class);
        map.setKey(key);
        map.getQuestionRef().setModel(question);
        map.getInterviewRef().setModel(interview);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(map);
        tx.commit();
        return map;
    }
}
