package com.community.service;

import com.community.model.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {

    /**
     * 分页查询文章列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Topic> findTopics(String keyword,Integer pageNumber , Integer pageSize,String orderFiled);

    /**
     * 通过话题Id分页查询文章列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Topic> findTopicsBySubjectId(String subjectId,Integer pageNumber , Integer pageSize,String orderFiled);

    /**
     * 通过ID查找topic
     * @param topicId
     * @return
     */
    Topic findTopicByTopicId(String topicId);

    /**
     * 通过userId查找文章列表
     * @param userId
     * @return
     */
    Page<Topic> findTopicsByUserId(String userId,Integer pageNumber , Integer pageSize,String orderFiled);

    /**
     * 新增文章
     * @param topic
     */
    String insert(Topic topic);

    /**
     * 更新文章
     * @param topicId
     */
    void updateReplyCountByTopicId(String topicId);
}
