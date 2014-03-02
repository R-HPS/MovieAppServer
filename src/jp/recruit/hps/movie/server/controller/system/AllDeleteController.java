package jp.recruit.hps.movie.server.controller.system;

import java.util.ArrayList;
import java.util.List;

import jp.recruit.hps.movie.server.meta.CompanyMeta;
import jp.recruit.hps.movie.server.meta.InterviewMeta;
import jp.recruit.hps.movie.server.meta.InterviewQuestionMapMeta;
import jp.recruit.hps.movie.server.meta.QuestionMeta;
import jp.recruit.hps.movie.server.meta.UserMeta;
import jp.recruit.hps.movie.server.model.Company;
import jp.recruit.hps.movie.server.model.Interview;
import jp.recruit.hps.movie.server.model.InterviewQuestionMap;
import jp.recruit.hps.movie.server.model.Question;
import jp.recruit.hps.movie.server.model.User;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class AllDeleteController extends Controller {

    @Override
    public Navigation run() throws Exception {
        List<Key> keys = new ArrayList<Key>();
        
        List<User> allUsers = Datastore.query(UserMeta.get()).asList();
        for (User s : allUsers) {
            keys.add(s.getKey());
        }
        
        List<Company> allCompanys = Datastore.query(CompanyMeta.get()).asList();
        for (Company s : allCompanys) {
            keys.add(s.getKey());
        }
        
        List<Interview> allInterviews = Datastore.query(InterviewMeta.get()).asList();
        for (Interview s : allInterviews) {
            keys.add(s.getKey());
        }
        
        List<InterviewQuestionMap> allInterviewQuestionMaps = Datastore.query(InterviewQuestionMapMeta.get()).asList();
        for (InterviewQuestionMap s : allInterviewQuestionMaps) {
            keys.add(s.getKey());
        }
        
        List<Question> allQuestions = Datastore.query(QuestionMeta.get()).asList();
        for (Question s : allQuestions) {
            keys.add(s.getKey());
        }
        Datastore.delete(keys);
        return null;

    }
}
