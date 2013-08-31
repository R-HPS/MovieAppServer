package jp.recruit.hps.movie.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.recruit.hps.movie.server.meta.MovieMeta;
import jp.recruit.hps.movie.server.model.Movie;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class MovieService {
    private static MovieMeta meta = MovieMeta.get();

    private static Movie createMovie(Map<String, Object> input, User user) {
        Movie movie = new Movie();
        Key key = Datastore.allocateId(Movie.class);
        BeanUtil.copy(input, movie);
        movie.setKey(key);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(movie);
        tx.commit();
        return movie;
    }

    public static Movie createMovie(String fileName, Date uploadDate, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fileName", fileName);
        map.put("uploadDate", uploadDate);
        return createMovie(map, user);
    }

    public static Movie getMovieByFileNameAndUserKey(String fileName, Key userKey) {
        try {
            return Datastore
                .query(meta)
                .filter(meta.fileName.equal(fileName), meta.userRef.equal(userKey))
                .asSingle();
        } catch (Exception e) {
            return null;
        }
    }
}
