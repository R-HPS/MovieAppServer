package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.NodeMeta;
import jp.recruit.hps.movie.server.model.Node;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class NodeService {
    private static NodeMeta meta = NodeMeta.get();

    public static Node createNode(Map<String, Object> input, User owner,
            Node parent) {
        Node node = new Node();
        Key key = Datastore.allocateId(Node.class);
        node.setKey(key);
        node.getUserRef().setModel(owner);
        node.getParentNodeRef().setModel(parent);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(node);
        tx.commit();
        return node;
    }

    public static Node createNode(User owner, Node parent, String question,
            String answer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("question", question);
        if (answer != null) {
            map.put("answer", answer);
        }
        return createNode(map, owner, parent);
    }

    public static Node createNode(User owner, Node parent, String question) {
        return createNode(owner, parent, question, null);
    }

    public static List<Node> getNodeListByUserKey(Key key) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.userRef.equal(key))
                .asList();

        } catch (Exception e) {
            return null;
        }
    }

}
