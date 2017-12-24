package com.community.service.impl;

import com.community.model.Subject;
import com.community.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 新增话题
     *
     * @param subject
     */
    public void insert(Subject subject) {
        subject.setSubjectId(UUID.randomUUID().toString());
        subject.setCreateTime(new Date());
        mongoTemplate.insert(subject);
    }

    /**
     * 获取所有话题
     */
    public List<Subject> getSubjects() {
       return mongoTemplate.findAll(Subject.class);
    }

    /**
     * 通过name查找话题
     *
     * @param name
     */
    public List<Subject> getSubjectByName(String name) {
       return mongoTemplate.find(new Query(Criteria.where("name").is(name)),Subject.class);
    }
}
