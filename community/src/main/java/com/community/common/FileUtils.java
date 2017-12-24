package com.community.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    public static String uploadImg(MultipartFile file, HttpServletRequest request) {
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
            try{
                file.transferTo(targetFile);
            }catch (IOException e){
                e.printStackTrace();
            }
            return "http://localhost:8080/cmt/images/"+targetFile.getName();
        }else {
            return null;
        }

    }
}
