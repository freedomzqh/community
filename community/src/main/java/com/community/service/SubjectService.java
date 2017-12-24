package com.community.service;

import com.community.model.Subject;

import java.util.List;

/**
 * 话题接口
 */
public interface SubjectService {
    /**
     * 新增话题
     * @param subject
     */
    void insert(Subject subject);

    /**
     *获取所有话题
     * @param
     */

    List<Subject> getSubjects();

    /**
     *通过name查找话题
     * @param name
     */
    List<Subject> getSubjectByName(String name);

}
