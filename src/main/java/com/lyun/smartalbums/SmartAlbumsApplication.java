package com.lyun.smartalbums;

import com.lyun.smartalbums.thread.ValidationQueue;
import com.lyun.smartalbums.utils.HomePathUtils;
import com.lyun.smartalbums.utils.SQLUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SmartAlbumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartAlbumsApplication.class, args);
        init();
        loadThread();
        SQLUserUtils.testDatabase();
    }

    public static void init(){
        File dir = new File(HomePathUtils.getPath() +"/verification");
        dir.setWritable(true,false);
        if (!dir.exists())dir.mkdirs();
        System.out.println(dir.getPath());
    }

    private static void loadThread(){
        ValidationQueue  validationQueue = new ValidationQueue();
        Thread validationThread = new Thread(validationQueue);
        validationThread.start();
    }

}
