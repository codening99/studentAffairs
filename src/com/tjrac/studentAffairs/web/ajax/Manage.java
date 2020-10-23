package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.ExcelService;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.service.impl.ExcelServiceImpl;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 管理教师和学生信息
 */
@WebServlet("/manage")
public class Manage extends BaseServlet {
    /**
     * 导出学生信息
     * 地址：/manage?action=exportStudentInfo
     */
    public void exportStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExcelService excelService = new ExcelServiceImpl();
        excelService.exportInfo(req.getSession(), resp);
    }
    /**
     * 导出空模板
     * 地址：/manage?action=exportModelExcel
     */
    public void exportModelExcel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExcelService excelService = new ExcelServiceImpl();
        String path = req.getSession().getServletContext().getRealPath("/");
        resp.getWriter().write(excelService.exportModel(path));
    }

    /**
     * 添加信息(老师和学生）
     * 地址：/manage?action=addObject
     * 参数：参考READEME.md
     */
    public void addObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();

        if ( req.getAttribute("type")=="2") {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String teacher_sex = req.getParameter("teacher_sex");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            Teacher teacher=new Teacher(account,password,name,teacher_sex,competence_id);
            resp.getWriter().write(userService.addObject(req.getSession(), teacher));

        } else if (req.getAttribute("type")=="1") {
            String sno = req.getParameter("sno");
            String name = req.getParameter("name");
            String student_sex = req.getParameter("student_sex");
            String password = req.getParameter("password");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            String grade_name = req.getParameter("grade_name");
            String clazz_name = req.getParameter("clazz_name");
            String department_name = req.getParameter("department_name");
            String specialty_name = req.getParameter("specialty_name");
            String direction_name = req.getParameter("direction_name");
            Student student=new Student(sno,name,password,student_sex,competence_id,grade_name,department_name,specialty_name,direction_name,clazz_name);
            resp.getWriter().write(userService.addObject(req.getSession(), student));

        }
    }

    /**
     * 修改用户信息（老师和学生）
     * 地址：/manage?action=modifyObject
     * 参数：参考READEME.md
     */
    public void modifyObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        if (req.getAttribute("type")=="2") {
            String account = req.getParameter("account");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String teacher_sex = req.getParameter("teacher_sex");
            Integer competence_id = Integer.parseInt(req.getParameter("competence_id"));
            Teacher teacher=new Teacher(account,password,name,teacher_sex,competence_id);
            resp.getWriter().write(userService.addObject(req.getSession(), teacher));
            return;

        } else if (req.getAttribute("type")=="1") {
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
            Student student=new Student(sno,name,sex,password,competence_id,grade_name,department_name,specialty_name,direction_name,clazz_name);
            resp.getWriter().write(userService.modifyObject(req.getSession(), student));

        }
    }

    /**
     * 删除用户的信息（老师和学生）
     * 地址：/manage?action=delObject
     */
    public void delObject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        Object object = req.getAttribute("user");//得到调用该方法的用户对象
        resp.getWriter().write(userService.delObject(req.getSession(), object));

    }

    /**
     * 查看用户的信息（老师和学生）
     * 地址：/manage?action=selectObject
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