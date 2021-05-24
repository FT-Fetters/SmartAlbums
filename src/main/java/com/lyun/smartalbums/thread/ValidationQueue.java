package com.lyun.smartalbums.thread;

import com.lyun.smartalbums.utils.HomePathUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ValidationQueue implements Runnable{
    public static Map<String,String> validationMap = new HashMap<>();
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (String key : validationMap.keySet()) {
                String tmp = validationMap.get(key);
                String[] strings = tmp.split("&");
                int time = Integer.parseInt(strings[1]);
                time--;
                //System.out.println(key+","+time);
                if (time == 0){
                    validationMap.remove(key);
                    File tmpFile =  new File(HomePathUtils.getPath() +"/verification/"+key+".jpg");
                    tmpFile.setWritable(true,false);
                    System.out.println(tmpFile.delete());
                }else {
                    validationMap.put(key,strings[0]+"&"+time);
                }
            }
        }
    }

    public static void removeVerification(String verificationId){
        validationMap.remove(verificationId);
        File tmpFile =  new File(HomePathUtils.getPath() +"/verification/"+verificationId+".jpg");
        tmpFile.setWritable(true,false);
        tmpFile.delete();
    }
}
