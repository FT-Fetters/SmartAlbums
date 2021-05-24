package com.lyun.smartalbums.controller;

import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.thread.ValidationQueue;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.SQLUserUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Register {


    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject data, HttpServletResponse response, HttpServletRequest request){
        String username = data.getString("username");
        String password = data.getString("password");
        String email = data.getString("email");
        String inputVerificationCode = data.getString("code");

        String verificationId = CookieUtils.getCookie(request,"verificationId");
        //判断验证码是否存在或者验证码和id能疯匹配
        if (verificationId == null || !ValidationQueue.validationMap.get(verificationId).equals(inputVerificationCode)){
            JSONObject errorVerificationCode = new JSONObject();
            errorVerificationCode.put("msg","Error verification Code");
            return errorVerificationCode;
        }else {
            ValidationQueue.removeVerification(verificationId);
        }
        //判断用户名是否存在
        if (!SQLUserUtils.userExists(username)){
            SQLUserUtils.registerUser(username,password,email);
            JSONObject registerSuccess = new JSONObject();
            registerSuccess.put("msg","Register success");
            return registerSuccess;
        }else {
            JSONObject userExists = new JSONObject();
            userExists.put("msg","User already exists");
            return userExists;
        }
    }

}
