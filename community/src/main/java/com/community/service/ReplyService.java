package com.community.service;

import com.community.model.Reply;
import org.springframework.data.domain.Page;

/**
 * 回复相关接口的定义
 * @author HZQ
 */
public interface ReplyService {

    /**
     * 新增回复
     * @param reply
     */
    Reply insert(Reply reply);

    /**
     * 获取某个文章的所有回复
     * @param topicId
     * @return
     */
    Page<Reply> getReplysByTopicId(String topicId,String orderFiled);

    /**
     * 点赞
     * @param ReplyId
     * @return
     */
    void updateUpvoteCount(String ReplyId);
}
