package com.community.service;

import com.alibaba.fastjson.JSON;
import com.community.model.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mvc.xml"})
public class TopicServiceTest {

    @Autowired
    private TopicService topicService;

    @Test
    public void save(){
        Topic topic=new Topic();
        topic.setAuthorId("asdasdsa");
        topic.setCreateTime(new Date());
        topic.setState(1);
        topic.setTitle("第一次测试");
        topic.setContent("<p>测试内容</p>");
        topicService.insert(topic);
    }


    @Test
    public void find(){
       Object object= topicService.findTopics("sasa",0,5,null);
       System.out.print(JSON.toJSON(object));
    }
}
