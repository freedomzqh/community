package com.community.service.impl;

import com.community.common.HtmlUtils;
import com.community.common.state.TopicState;
import com.community.model.Topic;
import com.community.model.User;
import com.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 分页查询文章列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<Topic> findTopics(String keyword,Integer pageNumber, Integer pageSize,String orderFiled) {
        Query query=new Query();
        List<Sort.Order> orders=new ArrayList<Sort.Order>(2);
        orders.add(new Sort.Order(Sort.Direction.DESC,orderFiled));
        Pageable pageable=new PageRequest(pageNumber,pageSize,new Sort(orders));
        query.with(pageable);
        if(!StringUtils.isEmpty(keyword)){
            query.addCriteria(Criteria.where("title").regex(keyword));
        }
        query.addCriteria(Criteria.where("state").is(TopicState.NORMAL.getState()));
        Long count=mongoTemplate.count(query,Topic.class);
        List<Topic> topicList=mongoTemplate.find(query,Topic.class);
        Page<Topic> page=new PageImpl<Topic>(topicList,pageable,count);
        return page;
    }

    /**
     * 通过话题Id分页查询文章列表
     *
     * @param subjectId
     * @param pageNumber
     * @param pageSize
     * @param orderFiled
     * @return
     */
    public Page<Topic> findTopicsBySubjectId(String subjectId, Integer pageNumber, Integer pageSize, String orderFiled) {
        Query query=new Query();
        List<Sort.Order> orders=new ArrayList<Sort.Order>(2);
        orders.add(new Sort.Order(Sort.Direction.DESC,orderFiled));
        Pageable pageable=new PageRequest(pageNumber,pageSize,new Sort(orders));
        query.with(pageable);
        query.addCriteria(Criteria.where("subjectId").regex(subjectId));
        query.addCriteria(Criteria.where("state").is(TopicState.NORMAL.getState()));
        Long count=mongoTemplate.count(query,Topic.class);
        List<Topic> topicList=mongoTemplate.find(query,Topic.class);
        Page<Topic> page=new PageImpl<Topic>(topicList,pageable,count);
        return page;
    }

    /**
     * 通过ID查找topic
     *
     * @param topicId
     * @return
     */
    public Topic findTopicByTopicId(String topicId) {
       Topic topic=mongoTemplate.findById(topicId,Topic.class);
       if(topic!=null){
           Query query=new Query();
           Update update=new Update();
           query.addCriteria(Criteria.where("topicId").is(topicId));
           if(topic.getVisitCount()==null){
               update.set("visitCount",1);
               mongoTemplate.updateFirst(query,update,Topic.class);
           }else {
               update.set("visitCount",topic.getVisitCount()+1);
               mongoTemplate.updateFirst(query,update,Topic.class);
           }
       }
       return topic;
    }

    /**
     * 通过userId查找文章列表
     *
     * @param userId
     * @return
     */
    public Page<Topic> findTopicsByUserId(String userId,Integer pageNumber , Integer pageSize,String orderFiled) {
        Query query=new Query();
        List<Sort.Order> orders=new ArrayList<Sort.Order>(2);
        orders.add(new Sort.Order(Sort.Direction.DESC,orderFiled));
        Pageable pageable=new PageRequest(pageNumber,pageSize,new Sort(orders));
        query.with(pageable);
        query.addCriteria(Criteria.where("authorId").regex(userId));
        query.addCriteria(Criteria.where("state").is(TopicState.NORMAL.getState()));
        Long count=mongoTemplate.count(query,Topic.class);
        List<Topic> topicList=mongoTemplate.find(query,Topic.class);
        Page<Topic> page=new PageImpl<Topic>(topicList,pageable,count);
        return page;
    }

    /**
     * 新增文章
     *
     * @param topic
     */
    public String insert(Topic topic) {
        User user=mongoTemplate.findById(topic.getAuthorId(), User.class);
        topic.setAuthorName(user.getNickName());
        topic.setAvatarUrl(user.getAvatarUrl());
        topic.setTopicId(UUID.randomUUID().toString());
        topic.setSummary(HtmlUtils.getText(topic.getContent()));
        topic.setImgUrl(HtmlUtils.getImgUrl(topic.getContent()));
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        topic.setState(TopicState.NORMAL.getState());
        topic.setCollectCount(0);
        topic.setReplyCount(0);
        topic.setVisitCount(0);
        mongoTemplate.save(topic);
        return topic.getTopicId();
    }

    /**
     * 更新文章的评论次数
     *
     * @param topicId
     */
    public void updateReplyCountByTopicId(String topicId) {
        Topic topic=mongoTemplate.findById(topicId,Topic.class);
        Update update=new Update();
        update.set("replyCount",topic.getReplyCount()+1);
        mongoTemplate.updateFirst(new Query(Criteria.where("topicId").is(topic)),update,Topic.class);
    }
}
