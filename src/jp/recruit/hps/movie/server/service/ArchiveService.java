package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.ArchiveMeta;
import jp.recruit.hps.movie.server.model.Archive;
import jp.recruit.hps.movie.server.model.Company;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Transaction;

public class ArchiveService {
    public static ArchiveMeta meta = ArchiveMeta.get();

    public static Archive createArchive(Map<String, Object> input, Company company) {
        Archive archive = new Archive();
        Key key = Datastore.allocateId(Archive.class);
        BeanUtil.copy(input, archive);
        archive.setKey(key);
        archive.getCompanyRef().setModel(company);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(archive);
        tx.commit();
        return archive;
    }

    public static Archive createArchive(Company company, String name, String type, int year,
            String body) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("type", type);
        map.put("year", year);
        map.put("body", new Text(body));
        return createArchive(map, company);
    }

    public static List<Archive> getArchiveListByCompanyKey(Key companyKey) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.companyRef.equal(companyKey))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Archive getArchiveByName(String name) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.name.equal(name))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getArchiveCountByNameAndYear(String name, int year) {
        return Datastore
            .query(meta)
            .filter(meta.name.equal(name), meta.year.equal(year))
            .count();
    }
}
