package com.community.service.impl;

import com.community.entity.Author;
import com.community.model.Reply;
import com.community.model.User;
import com.community.service.ReplyService;
import com.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TopicService topicService;

    /**
     * 新增回复
     *
     * @param reply
     */
    public Reply insert(Reply reply) {
        User user=mongoTemplate.findById(reply.getAuthorId(), User.class);
        Author author=new Author();
        author.setNickName(user.getNickName());
        author.setAvatarUrl(user.getAvatarUrl());
        reply.setReplyId(UUID.randomUUID().toString());
        reply.setAvatarUrl(user.getAvatarUrl());
        reply.setAuthorName(user.getNickName());
        reply.setCreateTime(new Date());
        reply.setState(1);
        reply.setUpvoteCount(0L);
        mongoTemplate.insert(reply);
        topicService.updateReplyCountByTopicId(reply.getTopicId());
        return mongoTemplate.findById(reply.getReplyId(),Reply.class);
    }

    /**
     * 获取某个文章的所有回复
     *
     * @param topicId
     * @return
     */
    public Page<Reply> getReplysByTopicId(String topicId,String orderFiled) {
        Query query=new Query();
        Sort.Order order=new Sort.Order(Sort.Direction.DESC,orderFiled);
        Sort sort=new Sort(order);
        Pageable pageable=new PageRequest(0,1000,sort);
        query.with(pageable);
        query.addCriteria(Criteria.where("topicId").is(topicId).and("state").is(1));
        Long count=mongoTemplate.count(query,Reply.class);
        List<Reply> replies=mongoTemplate.find(query,Reply.class);
        Page<Reply> page=new PageImpl<Reply>(replies,pageable,count);
        return page;
    }

    /**
     * 点赞
     *
     * @param replyId
     * @return
     */
    public void updateUpvoteCount(String replyId) {
        Reply reply=mongoTemplate.findById(replyId,Reply.class);
        Update update=new Update();
        update.set("upvoteCount",reply.getUpvoteCount()+1);
        mongoTemplate.updateFirst(new Query(Criteria.where("replyId").is(replyId)),update,Reply.class);
    }
}
