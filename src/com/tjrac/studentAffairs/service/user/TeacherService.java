package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.config.Choose;
import com.tjrac.studentAffairs.domain.student.Clazz;
import com.tjrac.studentAffairs.domain.student.Direction;
import com.tjrac.studentAffairs.domain.user.Student;
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
     *
     * @param user 对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addUser(Student user);

    String modifyUser(Student user);

    String delUser(Student user);

    String selectStudent();

    Student selectStudentBySno(String sno);

    String selectTypeInfo();

    /**
     * 增删查改方向信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session   浏览器回话对象
     * @param direction 方向信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addDirection(HttpSession session, Direction direction);

    String modifyDirection(HttpSession session, Direction direction);

    String delDirection(HttpSession session, Direction direction);

    String selectDirection(HttpSession session);

    Direction selectDirectionByDirectionName(String directionName);

    /**
     * 增删查改班级信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session   浏览器回话对象
     * @param clazz 班级信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addClass(HttpSession session, Clazz clazz);

    String modifyClass(HttpSession session, Clazz clazz);

    String delClass(HttpSession session, Clazz clazz);

    String selectClass(HttpSession session);

    Clazz selectClassByClassName(String clazzName);

    /**
     * 查询选方向
     */
    Choose selectChooseByGid(HttpSession session, Integer gid);

    String addChoose(HttpSession session, Choose choose);

    String modifyChoose(HttpSession session, Choose choose);
}
