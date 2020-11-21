package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.student.Direction;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.ExcelService;
import com.tjrac.studentAffairs.service.impl.ExcelServiceImpl;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.service.user.impl.TeacherServiceImpl;
import com.tjrac.studentAffairs.service.user.proxy.TeacherServiceProxy;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;
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
     * 导出错误模板
     */
    public void exportFailureFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ExcelServiceImpl excelService = new ExcelServiceImpl();
        excelService.getFailureStream(req.getSession(), resp);

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
    public void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            Integer grade_id = Integer.valueOf(req.getParameter("grade_id"));
            Integer department_id = Integer.valueOf(req.getParameter("department_id"));
            Integer specialty_id = Integer.valueOf(req.getParameter("specialty_id"));
            Integer direction_id = Integer.valueOf(req.getParameter("direction_id"));
            Integer clazz_id = Integer.valueOf(req.getParameter("clazz_id"));
            resp.getWriter().write(teacherService.addUser(new Student(sno, name, md5Password, student_sex, Competence.COMP_STUDENT, grade_id, department_id, specialty_id, direction_id, clazz_id)));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "学生已经存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }
    }

    /**
     * 修改学生信息
     */
    public void updateStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        String sno = req.getParameter("sno"); //学号
        Integer id = null;
        try {
            id = Integer.valueOf(req.getParameter("student_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String md5Password = MD5.MD5Encrypt(password); //加密
        String student_sex = req.getParameter("student_sex");
        Integer grade_id = Integer.valueOf(req.getParameter("grade_id"));
        Integer department_id = Integer.valueOf(req.getParameter("department_id"));
        Integer specialty_id = Integer.valueOf(req.getParameter("specialty_id"));
        Integer direction_id = Integer.valueOf(req.getParameter("direction_id"));
        Integer clazz_id = Integer.valueOf(req.getParameter("clazz_id"));
        resp.getWriter().write(teacherService.modifyUser(new Student(id, sno, name, md5Password, student_sex, Competence.COMP_STUDENT, grade_id, department_id, specialty_id, direction_id, clazz_id)));
    }

    /**
     * 删除学生信息
     * 地址：/manage?action=deleteStudentInfo
     */
    public void deleteStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        Integer id = null;
        try {
            id = Integer.valueOf(req.getParameter("student_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        resp.getWriter().write(teacherService.delUser(new Student(id)));
    }

    /**
     * 获取方向信息列表
     * 地址：/manage?action=directionList
     */
    public void directionList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        resp.getWriter().write(teacherService.selectDirection(req.getSession()));
    }

    /**
     * 新增方向
     * 地址：/manage?action=addDirection
     */
    public void addDirection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        String direction_name = req.getParameter("direction_name");
        if (teacherService.selectDirectionByDirectionName(direction_name) == null) {
            Integer grade_id = Integer.getInteger(req.getParameter("grade_id"));
            Integer specialty_id = Integer.getInteger(req.getParameter("specialty_id"));
            Direction direction = new Direction(direction_name, grade_id, specialty_id);
            resp.getWriter().write(teacherService.addDirection(req.getSession(), direction));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "方向已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }
    }

    /**
     * 修改方向信息
     * 地址：/manage?action=updateDirection
     */
    public void updateDirection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        Integer direction_id = null;
        Integer grade_id = null;
        Integer specialty_id = null;
        try {
            direction_id = Integer.parseInt(req.getParameter("direction_id"));
            specialty_id = Integer.parseInt(req.getParameter("specialty_id"));
            grade_id = Integer.parseInt(req.getParameter("grade_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String direction_name = req.getParameter("direction_name");

        Direction direction = new Direction(direction_id, direction_name, grade_id, specialty_id);

        resp.getWriter().write(teacherService.modifyDirection(req.getSession(), direction));
    }

    /**
     * 删除方向信息
     * 地址：/manage?action=delDirection
     */
    public void delDirection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        Integer direction_id = null;
        try {
            direction_id = Integer.parseInt(req.getParameter("direction_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Direction direction = new Direction(direction_id);

        resp.getWriter().write(teacherService.delDirection(req.getSession(), direction));
    }

}