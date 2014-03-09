package jp.recruit.hps.movie.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.CompanyMeta;
import jp.recruit.hps.movie.server.model.Company;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class CompanyService {
    private static CompanyMeta meta = CompanyMeta.get();

    public static Company createCompany(Map<String, Object> input) {
        Company company = new Company();
        Key key = Datastore.allocateId(Company.class);
        BeanUtil.copy(input, company);
        company.setKey(key);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(company);
        tx.commit();
        return company;
    }

    public static Company createCompany(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("interviewCount", 0);
        return createCompany(map);
    }

    public static Company getCompany(Key key) {
        try {
            return Datastore.query(meta).filter(meta.key.equal(key)).asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static Company getCompanyByName(String name) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.name.equal(name))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Company> getCompanyList() {
        try {
            return Datastore.query(meta).asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Company> getCompanyList(String keyword) {
        try {
            return Datastore
                .query(meta)
                .filterInMemory(meta.name.contains(keyword))
                .asList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<Company> getPopularCompanyList(int limit) {
        try {
            return Datastore
                .query(meta)
                .sort(meta.interviewCount.desc)
                .limit(limit)
                .asList();
        } catch (Exception e) {
            return null;
        }
    }

    public static int getCompanyCount(String name) {
        return Datastore.query(meta).filter(meta.name.equal(name)).count();
    }
}
