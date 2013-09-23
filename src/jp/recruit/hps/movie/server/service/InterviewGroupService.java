package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.InterviewGroupMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.InterviewGroup;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class InterviewGroupService {
    private static InterviewGroupMeta meta = InterviewGroupMeta.get();

    public static InterviewGroup createInterviewGroup(
            Map<String, Object> input, Company company) {
        InterviewGroup interviewGroup = new InterviewGroup();
        Key key = Datastore.allocateId(InterviewGroup.class);
        BeanUtil.copy(input, interviewGroup);
        interviewGroup.setKey(key);
        interviewGroup.getCompanyRef().setModel(company);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(interviewGroup);
        tx.commit();
        return interviewGroup;
    }

    public static InterviewGroup createInterviewGroup(Company company, String phase) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phase", phase);
        return createInterviewGroup(map, company);
    }

    public static InterviewGroup getInterviewGroup(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static InterviewGroup getInterviewGroupByCompanyKeyAndPhase(
            Key companyKey, String phase) {
        try {
            return Datastore
                .query(meta)
                .filter(
                    meta.companyRef.equal(companyKey),
                    meta.phase.equal(phase))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
