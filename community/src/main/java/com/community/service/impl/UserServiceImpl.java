package com.community.service.impl;

import com.community.model.User;
import com.community.service.UserService;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 用户注册
     *
     * @param user
     */
    public User save(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setState(1);
        mongoTemplate.save(user);
        return mongoTemplate.findById(user.getUserId(),User.class);
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     */
    public User login(String userName, String password) {
        Query query=new Query();
        query.addCriteria(Criteria.where("userName").is(userName).and("password").is(password));
        return mongoTemplate.findOne(query,User.class);
    }

    /**
     * 更新用户头像
     *
     * @param userId
     * @param imgPath
     */
    public User updateImg(String userId, String imgPath) {
        Query query=new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        Update update=new Update();
        update.set("avatarUrl",imgPath);
        mongoTemplate.updateFirst(query,update,User.class);
        return mongoTemplate.findById(userId,User.class);
    }

    /**
     * 通过userName查找用户
     *
     * @param userName
     */
    public User findUserByUserName(String userName) {
        Query query=new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        return mongoTemplate.findOne(query,User.class);
    }

    /**
     * 通过ID查找用户
     *
     * @param userId
     */
    public User find(String userId) {
        return mongoTemplate.findById(userId,User.class);
    }

    /**
     * 查找所有用户
     */
    public List<User> findAll() {
        return null;
    }

    /**
     * 更新用户
     *
     * @param user
     */
    public void update(User user) {

    }

    /**
     * 删除用户
     *
     * @param userId
     */
    public void delete(String userId) {

    }
}
