package com.community.controller;

import com.community.common.JsonResult;
import com.community.model.Topic;
import com.community.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 分页获取文章
     * @param keyword
     * @param pageNumber
     * @return
     */
    @RequestMapping("getTopics")
    public Object getTopics(@RequestParam(value = "keyword" ,required = false) String keyword,
                            @RequestParam(value = "pageNumber" ,required = false,defaultValue = "0") Integer pageNumber,
                            @RequestParam(value = "pageSize" ,required = false,defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "orderFiled",required=false,defaultValue = "createTime")String orderFiled){
        JsonResult result=new JsonResult();
        try {
            result.setSuccesed(topicService.findTopics(keyword,pageNumber,pageSize,orderFiled),"获取文章成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }

    }

    /**
     * 根据话题id获取文章
     * @param subjectId
     * @param pageNumber
     * @return
     */
    @RequestMapping("getTopicsBySubjectId")
    public Object getTopicsBySubjectId(@RequestParam(value = "subjectId",required = false) String subjectId,
                            @RequestParam(value = "pageNumber" ,required = false,defaultValue = "0") Integer pageNumber,
                            @RequestParam(value = "pageSize" ,required = false,defaultValue = "10") Integer pageSize,
                            @RequestParam(value = "orderFiled",required=false,defaultValue = "createTime")String orderFiled){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(subjectId)){
            result.setFail("话题ID不能为空");
            return result;
        }
        try {
            result.setSuccesed(topicService.findTopicsBySubjectId(subjectId,pageNumber,pageSize,orderFiled),"获取文章成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }

    }
    /**
     * 根据用户id查询文章
     * @param userId
     * @param pageNumber
     * @param pageSize
     * @param orderFiled
     * @return
     */
    @RequestMapping("getTopicsByUserId")
    public Object getTopicsByUserId(@RequestParam(value = "userId" ,required = false) String userId,
                                    @RequestParam(value = "pageNumber" ,required = false,defaultValue = "0") Integer pageNumber,
                                    @RequestParam(value = "pageSize" ,required = false,defaultValue = "10") Integer pageSize,
                                    @RequestParam(value = "orderFiled",required=false,defaultValue = "createTime")String orderFiled){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(userId)){
            result.setFail("用户ID不能为空");
            return result;
        }
        try {
           result.setSuccesed(topicService.findTopicsByUserId(userId,pageNumber,pageSize,orderFiled),"获取文章成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }

    /**
     * 根据ID查询文章
     * @param topicId
     * @return
     */
    @RequestMapping("getTopicById")
    public Object getTopics(String topicId){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(topicId)){
            result.setFail("文章ID不能为空");
            return result;
        }
        try {
            Topic topic=topicService.findTopicByTopicId(topicId);
            if(topic==null){
                result.setFail("文章不存在");
            }else {
                result.setSuccesed(topic,"获取文章成功");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }

    }

    /**
     * 保存文章
     * @param topic
     * @return
     */
    @RequestMapping(value = "saveTopic",method = RequestMethod.POST)
    public Object saveTopic(@Valid Topic topic, BindingResult bindingResult){
        JsonResult result=new JsonResult();
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            ObjectError oe = list.get(0);
            result.setFail(oe.getDefaultMessage());
            return result;
        }
        try {
            String topicId=topicService.insert(topic);
            result.setSuccesed(topicId,"保存成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }
}
