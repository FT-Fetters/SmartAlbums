package com.lyun.smartalbums.controller;


import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.HomePathUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class GetImage {
    @GetMapping(value="/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@RequestParam String classification,@RequestParam String imageName, HttpServletResponse response, HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if ((!(LoginUser.getLoginUsers().containsKey(username) &&
                loginId.equals(LoginUser.getLoginUsers().get(username).getLoginId())))){
            System.out.println("未登录");
            return null;
        }
        String baseUrl = HomePathUtils.getPath() + "/userImage/" + username;
        File userFolder = new File(baseUrl);
        if (userFolder.exists()){
            String imagePath = baseUrl + "/"+"classification" +"/"+classification+"/"+ imageName;
            File imageFile = new File(imagePath);
            if (imageFile.exists()){
                try {
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes,0,fileInputStream.available());
                    fileInputStream.close();
                    System.out.println("返回成功");
                    return bytes;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("未知错误");
                    return null;
                }
            }else {
                System.out.println("文件不存在");
                return null;
            }
        }else {
            System.out.println("用户文件夹不存在");
            return null;
        }
    }
}
