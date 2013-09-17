package jp.recruit.hps.movie.server.api;

import java.util.logging.Logger;

import javax.inject.Named;

import jp.recruit.hps.movie.common.CommonConstant;
import jp.recruit.hps.movie.server.api.dto.NodeV1Dto;
import jp.recruit.hps.movie.server.api.dto.ResultV1Dto;
import jp.recruit.hps.movie.server.model.Node;
import jp.recruit.hps.movie.server.model.User;
import jp.recruit.hps.movie.server.service.NodeService;
import jp.recruit.hps.movie.server.service.UserService;

import org.slim3.datastore.Datastore;

import com.google.api.server.spi.config.Api;

/**
 *
 */
@Api(name = "nodeEndpoint", version = "v1")
public class NodeV1Endpoint {
    private final static Logger logger = Logger.getLogger(NodeV1Endpoint.class
        .getName());

    private static final String SUCCESS = CommonConstant.SUCCESS;
    private static final String FAIL = CommonConstant.FAIL;

    public ResultV1Dto saveTree(@Named("root") NodeV1Dto root,
            @Named("userKey") String userKey) {
        ResultV1Dto result = new ResultV1Dto();
        try {
            User user =
                UserService.getUserByKey(Datastore.stringToKey(userKey));
            if (user == null) {
                result.setResult(FAIL);
            } else {
                createNode(user, null, root);
                result.setResult(SUCCESS);
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
            result.setResult(FAIL);
        }
        return result;
    }

    private void createNode(User owner, Node parent, NodeV1Dto dto) {
        Node node = null;
        if (dto.getAnswer() == null) {
            node = NodeService.createNode(owner, parent, dto.getQuestion());
        } else {
            node =
                NodeService.createNode(
                    owner,
                    parent,
                    dto.getQuestion(),
                    dto.getAnswer());
        }
        for (NodeV1Dto child : dto.getChildNodes()) {
            createNode(owner, node, child);
        }
    }
}
