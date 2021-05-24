package com.lyun.smartalbums.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@RestController
public class Login {

    @PostMapping("/Login")
    public JSONObject LoginAPI(@RequestBody JSONObject data, HttpServletResponse response){
        String username = data.getString("username");
        String pwd = data.getString("pwd");
        JSONObject resJson = new JSONObject();
        if (username.equals("test1") && pwd.equals("testpwd")){
            String loginId = DigestUtils.md5DigestAsHex((System.currentTimeMillis() + pwd).getBytes(StandardCharsets.UTF_8));
            LoginUser lg = new LoginUser(loginId);
            CookieUtils.writeCookie(response,"username","test1");
            CookieUtils.writeCookie(response,"loginId",loginId);
            LoginUser.addLoginUser(username,lg);
            resJson.put("MSG","SUCCESS");
        }else{
            resJson.put("MSG","ERROR");
        }
        return resJson;
    }

}
