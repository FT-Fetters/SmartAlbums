package com.lyun.smartalbums.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class HomePathUtils {
    public static String getPath(){
        try {
            String path =new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
