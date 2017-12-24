package com.community.controller;

import com.community.common.JsonResult;
import com.community.model.Subject;
import com.community.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "getSubjects")
    public Object getSubjects(){
        JsonResult result=new JsonResult();
        try {
            result.setSuccesed(subjectService.getSubjects(),"获取话题成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public Object insert(@Valid  Subject subject , BindingResult bindingResult){

        JsonResult result=new JsonResult();
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            ObjectError oe = list.get(0);
            result.setFail(oe.getDefaultMessage());
            return result;
        }
        try {
            List<Subject> subjects=subjectService.getSubjectByName(subject.getName());
            if(subjects!=null&&subjects.size()>0){
                result.setFail("话题名称不能重复！");
                return result;
            }
            subjectService.insert(subject);
            result.setSuccesed(null,"新增成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }

    }
}
