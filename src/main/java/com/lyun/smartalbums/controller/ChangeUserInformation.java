package com.lyun.smartalbums.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.SQLUserUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class ChangeUserInformation {

    @PostMapping("/changeUserInf")
    public static JSONObject changeUserInf(@RequestBody JSONObject data,
                                           HttpServletResponse response,
                                           HttpServletRequest request){
        String username = data.getString("username");
        String nickname = data.getString("nickname");
        String email = data.getString("email");
        String synopsis = data.getString("synopsis");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if (LoginUser.getLoginUsers().get(username).getLoginId().equals(loginId)){
            HashMap<String,String> inf = new HashMap<>();
            inf.put("nickname",nickname);
            inf.put("email",email);
            inf.put("synopsis",synopsis);
            SQLUserUtils.changeUserInf(inf,username);
            JSONObject success = new JSONObject();
            success.put("success",true);
            success.put("msg","Change success!");
            return success;
        }else {
            JSONObject fail = new JSONObject();
            fail.put("success",false);
            fail.put("msg","Not login!");
            return fail;
        }
    }
}
