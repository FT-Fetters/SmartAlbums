package com.lyun.smartalbums.controller;

import com.lyun.smartalbums.thread.ValidationQueue;
import com.lyun.smartalbums.utils.CookieUtils;
import com.lyun.smartalbums.utils.VerificationUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class VerificationCode {

    @GetMapping(value = "/VerificationCode",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] vCode(HttpServletResponse response){
        String tmp = VerificationUtils.code();
        String[] strings = tmp.split("&");
        String verificationId = strings[2];
        String filePath = strings[0];
        String code = strings[1];
        ValidationQueue.validationMap.put(verificationId,code+"&120");
        CookieUtils.writeCookie(response,"verificationId",verificationId);
        File verificationFile = new File(filePath);
        if (verificationFile.exists()){//判断该验证码文件是否存在
            try {
                FileInputStream inputStream = new FileInputStream(verificationFile);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes,0,inputStream.available());
                inputStream.close();
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


}
