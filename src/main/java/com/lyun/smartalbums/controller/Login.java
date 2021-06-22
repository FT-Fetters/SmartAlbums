package com.lyun.smartalbums.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.SQLUserUtils;
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
        if (SQLUserUtils.matchPassword(username,pwd)){
            String loginId = DigestUtils.md5DigestAsHex((System.currentTimeMillis() + pwd).getBytes(StandardCharsets.UTF_8));
            LoginUser lg = new LoginUser(loginId);
            CookieUtils.writeCookie(response,"username",username);
            CookieUtils.writeCookie(response,"loginId",loginId);
            LoginUser.addLoginUser(username,lg);
            resJson.put("MSG","SUCCESS");
        }else{
            resJson.put("MSG","ERROR");
        }
        return resJson;
    }

}
