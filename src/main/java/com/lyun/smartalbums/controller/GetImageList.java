package com.lyun.smartalbums.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lyun.smartalbums.bean.LoginUser;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.HomePathUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
public class GetImageList {

    /**
     * 用于获取图片列表的接口
     * @param response rep
     * @param request req
     * @return msg 消息  data如果获取到图片则返回图片列表
     */
    @GetMapping("/getImageList")
    public JSONObject getImageList(HttpServletResponse response, HttpServletRequest request){
        String username = CookieUtils.getCookie(request,"username");
        String loginId = CookieUtils.getCookie(request,"loginId");
        if (username == null || loginId == null || !LoginUser.getLoginUsers().get(username).getLoginId().equals(loginId)){
            JSONObject notLogin = new JSONObject();
            notLogin.put("msg","Not login");
            return notLogin;
        }
        //用户图像的存储方式使用树形文件存储,若使用单文件存储,每次存取图片时都需要对文件进行解码和编码,会导致消耗更多的性能
        String basePath = HomePathUtils.getPath()+"/userImage";
        String userFolder = basePath + "/" + username;
        File userFile = new File(userFolder);
        userFile.setWritable(true,false);
        if (!userFile.exists()){
            userFile.mkdirs();
            JSONObject emptyFile = new JSONObject();
            emptyFile.put("msg","Empty");
            return emptyFile;
        }
        File[] userFiles = userFile.listFiles();
        JSONObject returnImage = new JSONObject();
        returnImage.put("msg","Success");
        JSONArray imageList = new JSONArray();
        for (File file : userFiles) {
            imageList.add(file.getName());
        }
        returnImage.put("data",imageList);
        return returnImage;
    }
}
