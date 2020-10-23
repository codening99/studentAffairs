package com.tjrac.studentAffairs.service;

import javax.servlet.http.HttpSession;

/**
 * UserService User类业务层接口
 *
 * @author : xziying
 * @create : 2020-10-15 09:58
 */
public interface UserService {
    /*
        约定session对象：
        登录成功后，把登录对象信息存放到session的user字段里，用于其他方法判断在线状态
     */
    /**
     * 学生端和教师端的登录接口
     * @param session 浏览器回话对象
     * @param type  类型（1：教师登录  2：学生登录）
     * @param account   账号
     * @param password  密码
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String login(HttpSession session,Integer type, String account, String password);

    /**
     * 返回在线状态信息
     * @param session   浏览器回话对象
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String getOnlineStatus(HttpSession session);


    /**
     * 注销登录
     * @param session   浏览器回话对象
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String logout(HttpSession session);

}
