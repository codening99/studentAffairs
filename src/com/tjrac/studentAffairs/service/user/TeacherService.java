package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.user.User;
import com.tjrac.studentAffairs.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * TeacherService 老师业务层接口
 *
 * @author : xziying
 * @create : 2020-10-23 17:39
 */
public interface TeacherService extends UserService {
    /**
     * 增删查改学生和老师账号信息 先检测是否拥有1(管理员权限)权限的账号进行
     * @param session 浏览器回话对象
     * @param user   对象信息
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addUser(HttpSession session, User user);
    String modifyUser(HttpSession session, User user);
    String delUser(HttpSession session, User user);
    String selectUser(HttpSession session, Class<?> c);
}
