package com.tjrac.studentAffairs.service.user;

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
     * @param session   浏览器会话对象
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String getStudentInfo(HttpSession session);

}
