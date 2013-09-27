package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.SelectionMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Selection;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class SelectionService {
    private static SelectionMeta meta = SelectionMeta.get();

    public static Selection createSelection(
            Map<String, Object> input, Company company) {
        Selection Selection = new Selection();
        Key key = Datastore.allocateId(Selection.class);
        BeanUtil.copy(input, Selection);
        Selection.setKey(key);
        Selection.getCompanyRef().setModel(company);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(Selection);
        tx.commit();
        return Selection;
    }

    public static Selection createSelection(Company company, String phase) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phase", phase);
        return createSelection(map, company);
    }

    public static Selection getSelection(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<Selection> getSelectionListByCompanyKey(
            Key companyKey) {
        try {
            return Datastore
                .query(meta)
                .filter(
                    meta.companyRef.equal(companyKey))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static Selection getSelectionByCompanyKeyAndPhase(
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
