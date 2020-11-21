package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * StudentService 学生业务层接口
 *
 * @author : xziying
 * @create : 2020-10-23 17:28
 */
public interface StudentService extends UserService {
    /**
     * 获取学生对象信息
     *
     * @param session 浏览器会话对象
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String getStudentInfo(HttpSession session);

    /**
     * 修改学生密码
     *
     * @param session  浏览器会话对象
     * @param old      旧密码
     * @param password 新密码
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String changePassword(HttpSession session, String old, String password);

    /**
     * 根据学生学号查询学生
     *
     * @param sno 要查询的学生学号
     * @return 被查询到的学生
     */
    Student selectStudentBySno(String sno);

    /**
     * 查询选方向是否开启
     *
     * @param session 浏览器会话对象
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String queryChooserByGid(HttpSession session);
}
