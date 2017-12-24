package com.community.controller;

import com.community.common.FileUtils;
import com.community.common.JsonResult;
import com.community.model.User;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户接口
 */
@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Object register(@Valid User user ,BindingResult bindingResult){
        JsonResult result=new JsonResult();
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            ObjectError oe = list.get(0);
            result.setFail(oe.getDefaultMessage());
            return result;
        }
        try {
            User user1=userService.findUserByUserName(user.getUserName());
            if(user1!=null){
                result.setFail("该用户名已存在");
            }else {
                result.setSuccesed(userService.save(user),"注册成功");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }

    /**
     * 用户登陆
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Object login(String userName, String password){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            result.setFail("用户名或密码不能为空");
            return result;
        }
        try {
            User user=userService.login(userName,password);
            if(user==null){
                result.setFail("用户名或密码错误");
            }else {
                result.setSuccesed(user,"登陆成功");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }

    /**
     * 修改用户头像
     * @param userId
     * @param img
     * @return
     */
    @RequestMapping(value = "updateImg",method = RequestMethod.POST)
    public Object login(String userId, MultipartFile img,HttpServletRequest request){
        JsonResult result=new JsonResult();
        if(StringUtils.isEmpty(userId) || img.isEmpty()){
            result.setFail("用户和头像不能为空");
            return result;
        }
        try {
            String path=FileUtils.uploadImg(img,request);
            if(StringUtils.isEmpty(path)){
                result.setFail("文件类型不正确");
                return result;
            }
            result.setSuccesed(userService.updateImg(userId,path),"头像更新成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }
    }



}
