package jp.recruit.hps.movie.server.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.container.StringListContainer;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Friend;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.FriendService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "friendEndpoint", version = "v1")
public class FriendV1EndPoint {
    private final static Logger logger = Logger
        .getLogger(FriendV1EndPoint.class.getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public ResultV1Dto importFriend(@Named("userKey") String userKey,
            StringListContainer emailListContainer) {
        ResultV1Dto result = new ResultV1Dto();
        User user = UserService.getUserByKey(Datastore.stringToKey(userKey));
        try {
            if (user == null) {
                logger.warning("user not found");
                result.setResult(FAIL);
            } else {
                /* ソート済ユーザリスト */
                List<User> userList =
                    UserService.getUserListByEmailList(emailListContainer
                        .getList());
                List<Friend> friendList =
                    FriendService.getFriendList(Datastore.stringToKey(userKey));
                Collections.sort(friendList, new Comparator<Friend>() {
                    public int compare(Friend o1, Friend o2) {
                        return ((Long) o1.getTo().getKey().getId())
                            .compareTo(o2.getTo().getKey().getId());
                    }
                });
                for (User newFriend : userList) {
                    long id = newFriend.getId();
                    for (Friend friend : friendList) {
                        if (id < friend.getTo().getKey().getId()) {
                            FriendService.createFriend(user, newFriend);
                            break;
                        } else if (id == friend.getTo().getKey().getId()) {
                            break;
                        }
                    }
                }
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning("Exception" + e);
            result.setResult(FAIL);
        }
        return result;
    }

}
