package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.domain.user.User;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.http.HttpSession;
import java.util.List;
import org.junit.Test;

/**
 * TeacherServiceImpl 老师业务层实现类
 *
 * @author : xziying
 * @create : 2020-10-23 17:40
 */
public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {
    /**
     * 增删查改 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param session 浏览器回话对象
     * @param user  对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String addUser(HttpSession session, User user) {

        Object adminObj = session.getAttribute("user");
        /*Teacher adminObj = new Teacher();
        adminObj.setCompetence_id(3);*/

        Competence competence = getCompetence(adminObj);

        JsonPack json = new JsonPack();

        if (competence.test(competence.COMP_TEACHER) && user instanceof Student) { // 教师权限 新增学生
            int insert = studentDao.insert((Student) user); //存入数据库;//存入数据库
            if (insert == 1062){
                json.put("event", 2);
                json.put("msg", "学号已存在");
            } else if (insert == 0) {
                json.put("event", 0);
                json.put("msg", "添加成功");
            } else {
                json.put("event", 3);
                json.put("msg", "添加失败");
            }
        }
        else if (competence.test(competence.COMP_MANAGER) && user instanceof Teacher) { // 管理员权限 新增老师
            int insert = teacherDao.insert((Teacher) user);//存入数据库
            if (insert == 1062){
                json.put("event", 2);
                json.put("msg", "账户已存在");
            } else if (insert == 0) {
                json.put("event", 0);
                json.put("msg", "添加成功");
            } else {
                json.put("event", 3);
                json.put("msg", "添加失败");
            }
        } else { //没有添加的权限
            json.put("event", 1);
            json.put("msg", "权限不足");
        }
        return json.toJson();
    }

    @Override
    public String modifyUser(HttpSession session, User user) {

        Object adminObj = session.getAttribute("user"); //获取user
/*        Teacher adminObj = new Teacher();
        adminObj.setCompetence_id(3);*/

        JsonPack json = new JsonPack();

        Competence competence = getCompetence(adminObj);
        if (competence.test(competence.COMP_TEACHER) && user instanceof Student) { // 教师权限 修改学生
            int modify = studentDao.modify((Student) user);//修改
            if (modify == 404) {
                json.put("event", 2);
                json.put("msg", "没有该学号,无法修改");
            } else if (modify == 0) {
                json.put("event", 0);
                json.put("msg", "修改成功");
            } else {
                json.put("event", 3);
                json.put("msg", "修改失败");
            }
        }
        else if (competence.test(competence.COMP_MANAGER) && user instanceof Teacher) { // 管理员权限 修改老师
            int modify = teacherDao.modify((Teacher) user); //修改
            if (modify == 404) {
                json.put("event", 2);
                json.put("msg", "没有该账户,无法修改");
            } else if (modify == 0) {
                json.put("event", 0);
                json.put("msg", "修改成功");
            } else {
                json.put("event", 3);
                json.put("msg", "修改失败");
            }
        } else { //没有添加的权限
            json.put("event", 1);
            json.put("msg", "权限不足");
        }
        return json.toJson();
    }

    @Override
    public String delUser(HttpSession session, User user) {

        Object adminObj = session.getAttribute("user"); //获取user
        /*Teacher adminObj = new Teacher();
        adminObj.setCompetence_id(3);*/
        JsonPack json = new JsonPack();

        Competence competence = getCompetence(adminObj);

        if (competence.test(competence.COMP_TEACHER) && user instanceof Student) { // 教师权限 删除学生
            int delete = studentDao.delete((Student) user);//删除该学生

            if (delete == 0) {
                json.put("event", 0);
                json.put("msg", "删除成功");
            } else if (delete == 404){
                json.put("event", 2);
                json.put("msg", "没有找到学生信息，删除失败");
            } else {
                json.put("event", 3);
                json.put("msg", "删除失败");
            }
        }
        else if (competence.test(competence.COMP_MANAGER) && user instanceof Teacher) { // 管理员权限 删除老师
            int delete = teacherDao.delete((Teacher) user);//删除该账户信息
            if (delete == 0) {
                json.put("event", 0);
                json.put("msg", "删除成功");
            } else if (delete == 404){
                json.put("event", 2);
                json.put("msg", "没有找到账户信息，删除失败");
            } else {
                json.put("event", 3);
                json.put("msg", "删除失败");
            }
        } else { //没有添加的权限
            json.put("event", 1);
            json.put("msg", "权限不足");
        }
        return json.toJson();
    }

    @Override
    public String selectUser(HttpSession session, Class<?> c) {

        Object adminObj = session.getAttribute("user");

        Competence competence = getCompetence(adminObj);

        JsonPack json = new JsonPack();
        try {
            Object o = c.getConstructor().newInstance();
            if (competence.test(competence.COMP_TEACHER) && o instanceof Student) { // 教师权限 查询学生
                List<Student> students = studentDao.queryList();

                json.put("event", 0);
                json.put("count", students.size());
                json.put("students", students);
            } else if (competence.test(competence.COMP_MANAGER) && o instanceof Teacher) { // 管理员权限 查询老师
                List<Teacher> teachers = teacherDao.queryList();

                json.put("event", 0);
                json.put("count", teachers.size());
                json.put("teachers", teachers);
            } else {    //没有添加的权限
                json.put("event", 1);
                json.put("msg", "权限不足");
            }

        } catch (Exception e) {
            e.printStackTrace();
            json.put("event", 2);
            json.put("msg", "查询失败");
        }
        return json.toJson();
    }

    @Test
    public void test(){
        TeacherService teacherService= new TeacherServiceImpl();
        Teacher teacher = new Teacher(
                2,
                "test123",
                "123",
                "王老师",
                "男",
                1
        );
        teacherService.delUser(null, teacher);
    }
}
