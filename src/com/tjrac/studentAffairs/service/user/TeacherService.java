package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.UserService;

/**
 * TeacherService 老师业务层接口
 *
 * @author : xziying
 * @create : 2020-10-23 17:39
 */
public interface TeacherService extends UserService {
    /**
     * 增删查改学生和老师账号信息 先检测是否拥有1(管理员权限)权限的账号进行
     * @param user   对象信息
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addUser(Student user);
    String modifyUser(Student user);
    String delUser(Student user);
    String selectStudent();
    Boolean selectStudentBySno(String sno);
    String selectTypeInfo();
}
