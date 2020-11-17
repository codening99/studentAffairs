package com.tjrac.studentAffairs.service.user;

import com.tjrac.studentAffairs.domain.student.Department;
import com.tjrac.studentAffairs.domain.student.Grade;
import com.tjrac.studentAffairs.domain.student.Specialty;
import com.tjrac.studentAffairs.domain.user.Teacher;

import javax.servlet.http.HttpSession;

/**
 * ManagerService 管理员业务层接口
 *
 * @author : xziying
 * @create : 2020-10-23 18:14
 */
public interface ManagerService extends TeacherService {
    /**
     * 增删查改老师账号信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session 浏览器回话对象
     * @param user    对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addUser(HttpSession session, Teacher user);

    String modifyUser(HttpSession session, Teacher user);

    String delUser(HttpSession session, Teacher user);

    String selectTeacher(HttpSession session);

    Teacher selectTeacherByAccount(String account);

    /**
     * 增删查改年级信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session 浏览器回话对象
     * @param grade   年级信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addGrade(HttpSession session, Grade grade);

    String modifyGrade(HttpSession session, Grade grade);

    String delGrade(HttpSession session, Grade grade);

    String selectGrade(HttpSession session);

    Grade selectGradeByGradeName(String gradeName);

    /**
     * 增删查改系信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session    浏览器回话对象
     * @param department 系信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addDepartment(HttpSession session, Department department);

    String modifyDepartment(HttpSession session, Department department);

    String delDepartment(HttpSession session, Department department);

    String selectDepartment(HttpSession session);

    Department selectDepartmentByDepartmentName(String departmentName);

    /**
     * 增删查改专业信息 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session   浏览器回话对象
     * @param specialty 专业信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    String addSpecialty(HttpSession session, Specialty specialty);

    String modifySpecialty(HttpSession session, Specialty specialty);

    String delSpecialty(HttpSession session, Specialty specialty);

    String selectSpecialty(HttpSession session);

    Specialty selectSpecialtyBySpecialtyName(String specialtyName);
}
