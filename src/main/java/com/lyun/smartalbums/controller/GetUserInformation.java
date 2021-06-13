package com.lyun.smartalbums.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.SQLUserUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class GetUserInformation {

    @GetMapping("/getUserInf")
    public JSONObject getUserInformation(@RequestParam String username, HttpServletResponse response, HttpServletRequest request){
        String loginUsername = CookieUtils.getCookie(request,"username");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if (loginUsername.equals(username) && LoginUser.getLoginUsers().get(username).getLoginId().equals(loginId)){
            JSONObject getSuccess = new JSONObject();
            getSuccess.put("success",true);
            HashMap<String,String> inf = SQLUserUtils.getUserInf(username);
            getSuccess.put("nickname",inf.get("nickname"));
            getSuccess.put("email",inf.get("email"));
            getSuccess.put("synopsis",inf.get("synopsis"));
            return getSuccess;
        }else {
            JSONObject getFail = new JSONObject();
            getFail.put("success",false);
            return getFail;
        }

    }

}
