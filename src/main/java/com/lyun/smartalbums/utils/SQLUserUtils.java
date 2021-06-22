package com.lyun.smartalbums.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户的SQL工具类
 */

@Component
public class SQLUserUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static SQLUserUtils sqlUserUtils;

    @PostConstruct
    public void init(){
        sqlUserUtils = this;
        sqlUserUtils.jdbcTemplate = this.jdbcTemplate;
    }


    /**
     * 匹配用户名和密码
     * @param username 用户名
     * @param password 密码
     * @return 如果用户名和密码相对应则返回true
     */
    public static boolean  matchPassword(String username,String password){
        String sql = "SELECT PASSWORD FROM smartalbums_user WHERE username='"+username+"'";
        String sqlPwd = sqlUserUtils.jdbcTemplate.queryForObject(sql,String.class);
        return password.equals(sqlPwd);
    }


    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return 如果用户存则返回true
     */
    public static boolean userExists(String username){
        String sql = "SELECT id FROM smartalbums_user WHERE username='"+username+"'";
        List<Integer> list = sqlUserUtils.jdbcTemplate.queryForList(sql,Integer.class);
        return list.size() != 0;
    }


    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     */
    public static void registerUser(String username,String password,String email){
        String sql = "INSERT INTO smartalbums_user (username,PASSWORD,email) VALUES ('"+username+"','"+password+"','"+email+"')";
        sqlUserUtils.jdbcTemplate.execute(sql);
    }


    /**
     * 数据库测试方法
     */
    public static void testDatabase(){
        String sql = "SELECT PASSWORD FROM smartalbums_user WHERE username='admin'";
        String adminPwd = sqlUserUtils.jdbcTemplate.queryForObject(sql,String.class);
        System.out.println("数据库测试>管理员密码为:"+adminPwd);
    }

    public static HashMap<String,String> getUserInf(String username){
        String nickname_sql = "SELECT nickname FROM smartalbums_user WHERE username='"+username+"'";
        String email_sql = "SELECT email FROM smartalbums_user WHERE username='"+username+"'";
        String synopsis_sql = "SELECT synopsis FROM smartalbums_user WHERE username='"+username+"'";
        String nickname = sqlUserUtils.jdbcTemplate.queryForObject(nickname_sql,String.class);
        String email = sqlUserUtils.jdbcTemplate.queryForObject(email_sql,String.class);
        String synopsis = sqlUserUtils.jdbcTemplate.queryForObject(synopsis_sql,String.class);
        HashMap<String,String> res = new HashMap<>();
        res.put("nickname",nickname);
        res.put("email",email);
        res.put("synopsis", synopsis);
        return res;
    }

    public static void changeUserInf(HashMap<String,String> inf,String username){
        String nickname = inf.get("nickname");
        String email = inf.get("email");
        String synopsis = inf.get("synopsis");
        String sql = "UPDATE smartalbums_user SET nickname='"+nickname+"',synopsis='"+synopsis+"',email='"+email+"' WHERE username='"+username+"'";
        sqlUserUtils.jdbcTemplate.queryForObject(sql,String.class);
    }


}
