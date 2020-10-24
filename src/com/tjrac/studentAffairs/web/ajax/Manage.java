package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.ExcelService;
import com.tjrac.studentAffairs.service.impl.ExcelServiceImpl;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.service.user.impl.TeacherServiceImpl;
import com.tjrac.studentAffairs.service.user.proxy.TeacherServiceProxy;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.MD5;
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
     * 获取学生信息列表
     * 地址：/manage?action=studentList
     */
    public void studentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        resp.getWriter().write(teacherService.selectStudent());
    }

    /**
     * 获取年级列表
     * 地址：/manage?action=typeInfoList
     */
    public void typeInfoList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        resp.getWriter().write(teacherService.selectTypeInfo());
    }

    /**
     * 判断调用的是哪个方法
     * 地址：/manage?action=updateStudent
     *
     * @return 1:add 2:del 3:modify
     */
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String student_id = req.getParameter("student_id"); //学生id
        if (student_id == null) { //增加
            addStudentInfo(req, resp);
        } else { //修改
            updateStudentInfo(req, resp);
        }
    }

    /**
     * 增加学生信息
     */
    public void addStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        String sno = req.getParameter("sno"); //学号
        if (teacherService.selectStudentBySno(sno) == null) { //没有该学生
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            if ("".equals(password)) { //没有输入密码
                password = "123456"; //默认为123456
            }
            String md5Password = MD5.MD5Encrypt(password); //加密
            String student_sex = req.getParameter("student_sex");
            String grade_name = req.getParameter("grade_name");
            String department_name = req.getParameter("department_name");
            String specialty_name = req.getParameter("specialty_name");
            String direction_name = req.getParameter("direction_name");
            String clazz_name = req.getParameter("clazz_name");
            resp.getWriter().write(teacherService.addUser(new Student(sno, name, md5Password, student_sex, Competence.COMP_STUDENT, grade_name, department_name, specialty_name, direction_name, clazz_name)));
        } else {
            resp.getWriter().write("学生已经存在，加入失败");
        }
    }

    /**
     * 修改学生信息
     */
    public void updateStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        String sno = req.getParameter("sno"); //学号
        if (teacherService.selectStudentBySno(sno) == null) { //没有该学生
            resp.getWriter().write("学生不存在，修改失败");
        } else {
            String name = req.getParameter("name");
            String password = req.getParameter("password");
            String md5Password = MD5.MD5Encrypt(password); //加密
            String student_sex = req.getParameter("student_sex");
            String grade_name = req.getParameter("grade_name");
            String department_name = req.getParameter("department_name");
            String specialty_name = req.getParameter("specialty_name");
            String direction_name = req.getParameter("direction_name");
            String clazz_name = req.getParameter("clazz_name");
            resp.getWriter().write(teacherService.modifyUser(new Student(sno, name, md5Password, student_sex, Competence.COMP_STUDENT, grade_name, department_name, specialty_name, direction_name, clazz_name)));
        }
    }

    /**
     * 删除学生信息
     * 地址：/manage?action=deleteStudentInfo
     */
    public void deleteStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        String sno = req.getParameter("sno"); //学号
        Student student = teacherService.selectStudentBySno(sno);
        if (student == null) { //没有该学生
            resp.getWriter().write("学生不存在，删除失败");
        } else {
            resp.getWriter().write(teacherService.delUser(student));
        }
    }
}