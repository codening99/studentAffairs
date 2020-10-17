package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理教师和学生信息
 */
public class Manage extends BaseServlet {
    /**
     * 添加信息(老师和学生）
     * 地址：/manag?action=addObject
     * 参数：参考READEME.md
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void addObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Object object = req.getAttribute("user"); //得到调用该方法的用户对象
        if (object instanceof Teacher) {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String teacher_sex = req.getParameter("teacher_sex");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            ((Teacher) object).setAccount(account);
            ((Teacher) object).setPassword(password);
            ((Teacher) object).setName(name);
            ((Teacher) object).setCompetence_id(competence_id);
            ((Teacher) object).setTeacher_sex(teacher_sex);
            resp.getWriter().write(userService.addObject(req.getSession(), object));

        } else if ((object instanceof Student)) {
            String sno = req.getParameter("sno");
            String name = req.getParameter("name");
            String sex = req.getParameter("sex");
            String password = req.getParameter("password");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            String grade_name = req.getParameter("grade_name");
            String clazz_name = req.getParameter("clazz_name");
            String department_name = req.getParameter("department_name");
            String specialty_name = req.getParameter("specialty_name");
            String direction_name = req.getParameter("direction_name");
            ((Student) object).setSno(sno);
            ((Student) object).setPassword(password);
            ((Student) object).setName(name);
            ((Student) object).setStudent_sex(sex);
            ((Student) object).setCompetence_id(competence_id);
            ((Student) object).setGrade_name(grade_name);
            ((Student) object).setClazz_name(clazz_name);
            ((Student) object).setDepartment_name(department_name);
            ((Student) object).setDirection_name(direction_name);
            ((Student) object).setSpecialty_name(specialty_name);
            resp.getWriter().write(userService.addObject(req.getSession(), object));

        }
    }

    /**
     * 修改用户信息（老师和学生）
     * 地址：/manag?action=modifyObject
     * 参数：参考READEME.md
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void modifyObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Object object = req.getAttribute("user");//得到调用该方法的用户对象
        if (object instanceof Teacher) {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String teacher_sex = req.getParameter("teacher_sex");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            ((Teacher) object).setAccount(account);
            ((Teacher) object).setPassword(password);
            ((Teacher) object).setName(name);
            ((Teacher) object).setCompetence_id(competence_id);
            ((Teacher) object).setTeacher_sex(teacher_sex);
            resp.getWriter().write(userService.addObject(req.getSession(), object));
            return;

        } else if ((object instanceof Student)) {
            String sno = req.getParameter("sno");
            String name = req.getParameter("name");
            String sex = req.getParameter("sex");
            String password = req.getParameter("password");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            String grade_name = req.getParameter("grade_name");
            String clazz_name = req.getParameter("clazz_name");
            String department_name = req.getParameter("department_name");
            String specialty_name = req.getParameter("specialty_name");
            String direction_name = req.getParameter("direction_name");
            ((Student) object).setSno(sno);
            ((Student) object).setPassword(password);
            ((Student) object).setName(name);
            ((Student) object).setStudent_sex(sex);
            ((Student) object).setCompetence_id(competence_id);
            ((Student) object).setGrade_name(grade_name);
            ((Student) object).setClazz_name(clazz_name);
            ((Student) object).setDepartment_name(department_name);
            ((Student) object).setDirection_name(direction_name);
            ((Student) object).setSpecialty_name(specialty_name);
            resp.getWriter().write(userService.modifyObject(req.getSession(), object));

        }
    }

    /**
     * 删除用户的信息（老师和学生）
     * 地址：/manag?action=delObject
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void delObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Object object = req.getAttribute("user");//得到调用该方法的用户对象
        resp.getWriter().write(userService.delObject(req.getSession(), object));

    }

    /**
     * 查看用户的信息（老师和学生）
     * 地址：/manag?action=selectObject
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void selectObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Object object = req.getAttribute("user");//得到调用该方法的用户对象
        if (object instanceof Teacher) { //调用对象类型为Teacher类
            resp.getWriter().write(userService.selectObject(req.getSession(), Teacher.class));
        }
        else if(object instanceof Student){ //调用对象为Student类
            resp.getWriter().write(userService.selectObject(req.getSession(),Student.class));
        }
    }
}