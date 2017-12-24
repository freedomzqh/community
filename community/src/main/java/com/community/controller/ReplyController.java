package com.community.controller;

import com.community.common.JsonResult;
import com.community.model.Reply;
import com.community.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    /**
     * 查询评论
     * @param topicId
     * @param orderFiled
     * @return
     */
    @RequestMapping(value = "getReplysByTopicId")
    public Object getReplysByTopicId(@RequestParam(value = "topicId",required = false) String topicId,
                                     @RequestParam(value = "orderFiled",required = false ,defaultValue = "createTime")String orderFiled){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(topicId)){
            result.setFail("文章Id不能为空");
            return result;
        }
        try {
            result.setSuccesed(replyService.getReplysByTopicId(topicId,orderFiled),"获取评论成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }

    /**
     * 发表评论
     * @param reply
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public Object insert(@Valid Reply reply, BindingResult bindingResult){
        JsonResult result=new JsonResult();
        if(bindingResult.hasErrors()){
            result.setFail(bindingResult.getAllErrors().get(0).getDefaultMessage());
            return result;
        }
        try {
            result.setSuccesed(replyService.insert(reply),"保存评论成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }

    /**
     * 评论点赞
     * @param replyId
     * @return
     */
    @RequestMapping(value = "upVote")
    public Object upVote(@RequestParam(value = "replyId",required = false)String replyId){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(replyId)){
            result.setFail("评论id不能为空");
            return result;
        }
        try {
            replyService.updateUpvoteCount(replyId);
            result.setSuccesed(null,"点赞成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }
}
