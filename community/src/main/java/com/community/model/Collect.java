package com.community.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 收藏实体
 * @author HZQ
 */
@Document(collection = "collect")
public class Collect {
    @Id
    private String collectId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 文章ID
     */
    private String topicId;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
