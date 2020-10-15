package com.tjrac.studentAffairs.service;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;

import javax.servlet.http.HttpSession;

/**
 * UserService User类业务层接口
 *
 * @author : xziying
 * @create : 2020-10-15 09:58
 */
public interface UserService {
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
    /**
     * 增加删除修改教师信息 先检测是否拥有1(管理员权限)权限的账号进行
     * @param session 浏览器回话对象
     * @param teacher   教师信息对象
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addTeacher(HttpSession session, Teacher teacher);
    Student modifyTeacher(HttpSession session, Teacher teacher);
    Student delTeacher(HttpSession session, Teacher teacher);

    /**
     * 增加删除修改学生信息 先检测是否拥有1和2(管理员权限和教师权限)权限的账号进行
     * @param session 浏览器回话对象
     * @param student   学生对象
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addStudent(HttpSession session, Student student);
    String modifyStudent(HttpSession session, Student student);
    String delStudent(HttpSession session, Student student);



}
