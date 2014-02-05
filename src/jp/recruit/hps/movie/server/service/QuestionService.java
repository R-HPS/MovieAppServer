package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.QuestionMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Question;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class QuestionService {
    private static QuestionMeta meta = QuestionMeta.get();

    public static Question createQuestion(Map<String, Object> input, User user, Company company) {
        Question question = new Question();
        Key key = Datastore.allocateId(Question.class);
        BeanUtil.copy(input, question);
        question.setKey(key);
        question.getUserRef().setModel(user);
        question.getCompanyRef().setModel(company);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(question);
        tx.commit();
        return question;
    }

    public static Question createQuestion(User user, Company company, String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        return createQuestion(map, user, company);
    }

    public static Question getQuestion(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<Question> getQuestionListByKeyList(List<Key> keyList) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.key.in(keyList))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<Question> getQuestionListByCompanyKey(Key companyKey) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.companyRef.equal(companyKey))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
}
