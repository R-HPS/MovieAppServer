package jp.recruit.hps.movie.server.service;

import java.util.List;

import jp.recruit.hps.movie.server.meta.ReadMeta;
import jp.recruit.hps.movie.server.model.InterviewGroup;
import jp.recruit.hps.movie.server.model.Read;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class ReadService {
    private static ReadMeta meta = ReadMeta.get();

    public static Read createRead(User user, InterviewGroup interviewGroup) {
        Read read = new Read();
        Key key = Datastore.allocateId(Read.class);
        read.setKey(key);
        read.getUserRef().setModel(user);
        read.getInterviewGroupRef().setModel(interviewGroup);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(read);
        tx.commit();
        return read;
    }

    public static boolean isRead(Key userKey, Key interviewGroupKey) {
        try {
            if (Datastore
                .query(meta)
                .filter(
                    meta.userRef.equal(userKey),
                    meta.interviewGroupRef.equal(interviewGroupKey))
                .count() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Read> getReadList(Key userKey) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.userRef.equal(userKey))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
}
