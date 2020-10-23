package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     * @param object  对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String addAccount(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user");

        int competence = getCompetence(adminObj);

        JsonPack json = new JsonPack();

        //判断权限
        if (competence == 1) { //如果存在1权限

            if (object instanceof Teacher) {  //要添加的为老师

                //判断数据库是否已经有同账户名的老师
                Object teacherDB = teacherDao.query("account", ((Teacher) adminObj).getAccount());

                if (teacherDB != null) { //数据库中已存在该账户
                    json.put("event", 2);
                    json.put("msg", "账户已存在");
                } else {
                    teacherDao.insert((Teacher) adminObj); //存入数据库
                    json.put("event", 0);
                    json.put("msg", "添加成功");
                }
            } else {

                //判断数据库是否已经有同学号的学生
                Object teacherDB = studentDao.query("sno", ((Student) adminObj).getSno());

                if (teacherDB != null) { //数据库中已存在该账户
                    json.put("event", 2);
                    json.put("msg", "学号已存在");
                } else {
                    studentDao.insert((Student) adminObj); //存入数据库
                    json.put("event", 0);
                    json.put("msg", "添加成功");
                }
            }

        } else if (competence == 2) { //没有添加的权限
            json.put("event", 1);
            json.put("msg", "权限不足");
        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }

        return json.toJson();
    }

    @Override
    public String modifyAccount(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user"); //获取user
        JsonPack json = new JsonPack();

        int competence = getCompetence(adminObj); //获取权限

        if (competence == 1) { //有修改的权限

            if (object instanceof Teacher) { //要修改的是teacher

                Teacher teacherDB = teacherDao.query("account", ((Teacher) object).getAccount()); //数据库中寻找该对象

                if (teacherDB == null) { //没有该账户 无法更新
                    json.put("event", 2);
                    json.put("msg", "没有该账户,无法修改");
                } else { //找到该账户
                    teacherDao.modify((Teacher) object); //修改
                    json.put("event", 0);
                    json.put("msg", "修改成功");
                }

            } else if (object instanceof Student) { //要修改的是student

                Student studentDB = studentDao.query("sno", ((Student) object).getSno());

                if (studentDB == null) { //没有该账户 无法更新
                    json.put("event", 2);
                    json.put("msg", "没有该学生,无法修改");
                } else { //找到该账户
                    studentDao.modify((Student) object); //修改
                    json.put("event", 0);
                    json.put("msg", "修改成功");
                }

            }

        } else if (competence == 2) {
            json.put("event", 1);
            json.put("msg", "权限不足");
        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }

        return json.toJson();
    }

    @Override
    public String delAccount(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user"); //获取user
        JsonPack json = new JsonPack();

        int competence = getCompetence(adminObj); //获取权限

        if (competence == 1) { //有删除权限

            if (object instanceof Teacher) { //要删除的是老师

                Teacher teacherDB = teacherDao.query("account", ((Teacher) object).getAccount());

                if (teacherDB == null) { //数据库中没有该老师账户

                    json.put("event", 2);
                    json.put("msg", "没有找到账户信息，删除失败");

                } else { //找到该账户信息

                    teacherDao.delete((Teacher) object); //删除该账户信息

                    json.put("event", 0);
                    json.put("msg", "删除成功");

                }

            } else if (object instanceof Student) {

                Student studentDB = studentDao.query("sno", ((Student) object).getSno());

                if (studentDB == null) { //没有找到该学生信息

                    json.put("event", 2);
                    json.put("msg", "没有找到学生信息，删除失败");

                } else {

                    studentDao.delete((Student) object); //删除该学生

                    json.put("event", 0);
                    json.put("msg", "删除成功");

                }

            }

        } else if (competence == 2) {
            json.put("event", 1);
            json.put("msg", "权限不足");
        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }

        return json.toJson();
    }

    @Override
    public String selectAccount(HttpSession session, Class<?> c) {

        Object adminObj = session.getAttribute("user");

        int competence = getCompetence(adminObj);

        JsonPack json = new JsonPack();

        //判断权限
        if (competence == 1) { //如果存在1权限

            try {
                Object o = c.getConstructor().newInstance();

                if (o instanceof Teacher) { //为teacher

                    List<Teacher> teachers = teacherDao.queryList();

                    json.put("event", 0);
                    json.put("msg", "查看成功");
                    json.put("count", teachers.size());
                    json.put("teachers", teachers);

                } else { //为student

                    List<Student> students = studentDao.queryList();

                    json.put("event", 0);
                    json.put("msg", "查看成功");
                    json.put("count", students.size());
                    json.put("students", students);

                }

            } catch (Exception e) {
                json.put("event", 2);
                json.put("msg", "查找失败");
                e.printStackTrace();
            }

        } else if (competence == 2) {

            json.put("event", 1);
            json.put("msg", "没有该权限");

        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }


        return json.toJson();
    }
}
