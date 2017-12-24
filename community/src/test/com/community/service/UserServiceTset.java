package com.community.service;

import com.community.model.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml"})
public class UserServiceTset {
    private static Logger logger=Logger.getLogger(UserServiceTset.class);
    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setUserName("test");
        user.setPassword("111111");
        userService.save(user);

        logger.info("-------添加成功:-------");
    }

    @Test
    public void findALL() {
        System.out.print(new Date());
    }
}
