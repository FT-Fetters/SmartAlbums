package com.lyun.smartalbums.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.HomePathUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
public class UploadImage {

    @PostMapping("/uploadImage")
    public static JSONObject uploadImage(@RequestParam MultipartFile file,
                                         HttpServletResponse response,
                                         HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if (LoginUser.getLoginUsers().containsKey(username) &&
                Objects.equals(loginId, LoginUser.getLoginUsers().get(username).getLoginId())){
            //判断上传的文件是否为空
            if (file.isEmpty()){
                JSONObject fileEmpty = new JSONObject();
                fileEmpty.put("msg","Empty file");
                return fileEmpty;
            }
            File userImageDir = new File(HomePathUtils.getPath() + "/userImage" + "/" + username);
            userImageDir.setWritable(true,false);
            if (userImageDir.exists())userImageDir.mkdir();
            File userImageTempDir = new File(HomePathUtils.getPath() + "/userImage" + "/" + username + "/temp");
            userImageTempDir.setWritable(true,false);
            if (userImageTempDir.exists())userImageTempDir.mkdir();
            try {
                File userImage = new File(HomePathUtils.getPath() + "/userImage" + "/" + username+"/temp" + "/" + file.getOriginalFilename());
                file.transferTo(userImage);
                JSONObject uploadSuccess = new JSONObject();
                uploadSuccess.put("msg","Upload success");
                try {
                    //本地开发环境的时候使用python,上传到服务器的时候要改成python3
                    Runtime.getRuntime().exec("python " + HomePathUtils.getPath() + "/SmartalbumsClassification/classification.py");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return uploadSuccess;
            } catch (IOException e) {
                e.printStackTrace();
                JSONObject uploadError = new JSONObject();
                uploadError.put("msg","Upload Error");
                return uploadError;
            }
        }else {
            JSONObject notLogin = new JSONObject();
            notLogin.put("success",false);
            notLogin.put("msg","Not login!");
            return notLogin;
        }
    }

}
