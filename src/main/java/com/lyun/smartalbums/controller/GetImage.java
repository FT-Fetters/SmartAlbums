package com.lyun.smartalbums.controller;


import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.HomePathUtils;
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
    @GetMapping("/getImage")
    public byte[] getImage(@RequestParam String classification,@RequestParam String imageName, HttpServletResponse response, HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if (username == null || loginId == null || !LoginUser.getLoginUsers().get(username).getLoginId().equals(loginId)){
            return null;
        }
        String baseUrl = HomePathUtils.getPath() + "/userImage/" + username;
        File userFolder = new File(baseUrl);
        if (userFolder.exists()){
            String imagePath = baseUrl + "/" +classification+"/"+ imageName;
            File imageFile = new File(imagePath);
            if (imageFile.exists()){
                try {
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes,0,fileInputStream.available());
                    fileInputStream.close();
                    return bytes;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
