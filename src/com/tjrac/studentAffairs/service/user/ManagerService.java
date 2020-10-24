package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.domain.user.User;

import javax.servlet.http.HttpSession;

/**
 * ManagerService 管理员业务层接口
 *
 * @author : xziying
 * @create : 2020-10-23 18:14
 */
public interface ManagerService extends TeacherService{
    /**
     * 增删查改老师账号信息 先检测是否拥有1(管理员权限)权限的账号进行
     * @param session 浏览器回话对象
     * @param user   对象信息
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addUser(HttpSession session, Teacher user);
    String modifyUser(HttpSession session, Teacher user);
    String delUser(HttpSession session, Teacher user);
    String selectTeacher(HttpSession session);
}
