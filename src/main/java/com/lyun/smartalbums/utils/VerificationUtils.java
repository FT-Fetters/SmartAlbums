package com.lyun.smartalbums.utils;

import com.lyun.smartalbums.thread.ValidationQueue;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class VerificationUtils {
    private static final String[] strs={"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g",
            "h","j","k","l","z","x","c","v","b","n","m","1","2","3","4","5","6","7","8","9","0"};

    static final int WIDTH = 150;
    static final int HEIGHT = 50;
    static final Color[] COLORS = new Color[]{Color.cyan,Color.RED,Color.ORANGE,Color.BLACK,Color.GREEN,Color.GRAY,Color.BLUE};
    public static String code (){
        Random random=new Random();
        BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        Graphics2D g=(Graphics2D)image.getGraphics();
        g.setColor(COLORS[random.nextInt(COLORS.length)]);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(COLORS[random.nextInt(COLORS.length)]);
        g.setFont(new Font("仿宋",Font.BOLD,25));

        int x=25;
        int y=30;
        StringBuilder code = new StringBuilder();
        for (int i = 0; i <4; i++) {
            //4个验证码
            g.setColor(COLORS[random.nextInt(COLORS.length)]);
            int index=random.nextInt(strs.length);
            String str=strs[index];
            code.append(str);
            //旋转角度
            int jd=random.nextInt(100)-50;
            double hd=jd*Math.PI/180;
            g.rotate(hd,x,y);
            g.drawString(str,x,y);
            g.rotate(-hd,x,y);
            x+=20;
        }

        for (int i = 0; i <7; i++) {
            g.setColor(COLORS[random.nextInt(COLORS.length)]);
            int x1=random.nextInt(22);
            int y1=random.nextInt(50);
            int x2=random.nextInt(25)+120;
            int y2=random.nextInt(50);
            g.drawLine(x1,y1,x2,y2);
        }
        String fileName = DigestUtils.md5DigestAsHex((System.currentTimeMillis() + code.toString()).getBytes(StandardCharsets.UTF_8));
        try {
            File imageFile = new File(HomePathUtils.getPath()+"/verification/"+fileName+".jpg");
            imageFile.setWritable(true,false);
            ImageIO.write(image, "jpg", imageFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        ValidationQueue.validationMap.put(fileName,code+"&120");
        return HomePathUtils.getPath()+"/verification/"+fileName+".jpg"+"&"+code+"&"+fileName;
    }
}
