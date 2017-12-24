package com.community.controller;

import com.community.common.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/img")
public class ImgUploadController {
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Object upload(MultipartFile file, HttpServletRequest request){
        JsonResult result=new JsonResult();
        try {
            if(!file.isEmpty()){
                String path=null;// 文件路径
                String type=null;// 文件类型
                String fileName=file.getOriginalFilename();// 文件原名称
                type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
                if("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())){
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    path=realPath+"images"+System.getProperty("file.separator")+ UUID.randomUUID().toString()+"."+type;
                    File targetFile=new File(path);
                    if(!targetFile.exists()){
                        targetFile.mkdirs();
                    }
                    file.transferTo(targetFile);
                    result.setSuccesed("http://localhost:8080/cmt/images/"+targetFile.getName(),"文件上传成功");
                }else {
                    result.setFail("文件类型不正确！");
                }

            }else {
                result.setFail("文件不能为空！");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setFail("运行异常");
            return result;
        }

    }
}
