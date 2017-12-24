package com.community.service;

import com.community.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用户接口
 * @author HZQ
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    User save(User user);


    /**
     *用户登录
     * @param userName
     * @param password
     */
    User login(String userName,String password);


    /**
     *更新用户头像
     * @param userId
     * @param imgPath
     */
    User updateImg(String userId,String imgPath);

    /**
     * 通过ID查找用户
     * @param userName
     */
    User findUserByUserName(String userName);
    /**
     * 通过ID查找用户
     * @param userId
     */
    User find(String userId);

    /**
     * 查找所有用户
     * @param
     */
    List<User> findAll();

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 删除用户
     * @param userId
     */
    void delete(String userId);

}
