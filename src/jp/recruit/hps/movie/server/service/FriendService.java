package jp.recruit.hps.movie.server.service;

import java.util.List;

import jp.recruit.hps.movie.server.meta.FriendMeta;
import jp.recruit.hps.movie.server.model.Friend;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class FriendService {
    private static FriendMeta meta = FriendMeta.get();

    public static Friend createFriend(User from, User to) {
        Friend friend = new Friend();
        Key key = Datastore.allocateId(Friend.class);
        friend.setKey(key);
        friend.getFrom().setModel(from);
        friend.getTo().setModel(to);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(friend);
        tx.commit();
        return friend;
    }

    public static boolean isFriend(Key from, Key to) {
        try {
            if (Datastore
                .query(meta)
                .filter(meta.from.equal(from), meta.to.equal(to))
                .count() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public static List<Friend> getFriendList(Key from) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.from.equal(from))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
}
