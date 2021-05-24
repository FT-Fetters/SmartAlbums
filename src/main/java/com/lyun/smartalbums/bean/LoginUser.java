package com.lyun.smartalbums.bean;

import java.util.HashMap;

public class LoginUser {
    String loginId;
    static HashMap<String,LoginUser> loginUsers = new HashMap<>();

    public LoginUser(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public static HashMap<String,LoginUser> getLoginUsers() {
        return loginUsers;
    }

    public static void addLoginUser(String username,LoginUser loginUser){
        loginUsers.put(username,loginUser);
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "loginId='" + loginId + '\'' +
                '}';
    }
}
