package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.config.Choose;
import com.tjrac.studentAffairs.domain.student.Clazz;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理教师和学生信息
 *
 * @author ZeNing
 * @create 2020-10-23 20:15
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

    /**
     * 查询选方向情况
     * 地址：/manage?action=inquiryDirectionSelection
     */
    public void inquiryDirectionSelection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        HttpSession session = req.getSession();
        JsonPack json = new JsonPack();

        int gid = Integer.parseInt(req.getParameter("gid"));
        Choose chooseByGid = teacherService.selectChooseByGid(session, gid);

        if (chooseByGid == null) { //没有开启选方向

            json.put("event", 0);
            json.put("msg", "未开启选方向");
        } else {
            Integer status = chooseByGid.getStatus();
            if (status == 1) { //未过期
                json.put("event", 1);
                json.put("msg", "选方向开启中");
            } else { //已过期
                json.put("event", 0);
                json.put("msg", "方向已过期");
            }
        }

        resp.getWriter().write(json.toJson());
    }

    /**
     * 开启选方向
     * 地址：/manage?action=openDirectionSelection
     */
    public void openDirectionSelection(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        HttpSession session = req.getSession();

        int gid = Integer.parseInt(req.getParameter("gid"));
        Choose chooseByGid = teacherService.selectChooseByGid(session, gid); //查询该年级的选方向情况
        String endtime = req.getParameter("endtime"); //获取结束时间戳
        int grade_id = Integer.parseInt(req.getParameter("grade_id")); //获取开启选方向的年级

        Choose createChoose = new Choose(endtime, 1, grade_id);

        if (chooseByGid == null) { //没有开启过选方向
            resp.getWriter().write(teacherService.addChoose(session, createChoose));
        } else {
            chooseByGid.setEndtime(endtime);
            chooseByGid.setStatus(1);
            resp.getWriter().write(teacherService.modifyChoose(session, chooseByGid));
        }

    }

    /**
     * 新增班级
     * 地址：/manage?action=addClass
     */
    public void addClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        String clazz_name = req.getParameter("clazz_name");
        if (teacherService.selectClassByClassName(clazz_name) == null) {
            Integer grade_id = Integer.getInteger(req.getParameter("grade_id"));
            Integer specialty_id = Integer.getInteger(req.getParameter("specialty_id"));
            Clazz clazz = new Clazz(clazz_name, specialty_id, grade_id);
            resp.getWriter().write(teacherService.addClass(req.getSession(), clazz));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "班级已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }
    }

    /**
     * 修改班级信息
     * 地址：/manage?action=updateClass
     */
    public void updateClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        Integer clazz_id = null;
        Integer grade_id = null;
        Integer specialty_id = null;
        try {
            clazz_id = Integer.parseInt(req.getParameter("clazz_id"));
            specialty_id = Integer.parseInt(req.getParameter("specialty_id"));
            grade_id = Integer.parseInt(req.getParameter("grade_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String clazz_name = req.getParameter("clazz_name");

        Clazz clazz = new Clazz(clazz_id, clazz_name, specialty_id, grade_id);

        resp.getWriter().write(teacherService.modifyClass(req.getSession(), clazz));
    }

    /**
     * 删除班级信息
     * 地址：/manage?action=delClass
     */
    public void delClass(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();

        Integer clazz_id = null;
        try {
            clazz_id = Integer.parseInt(req.getParameter("clazz_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Clazz clazz = new Clazz(clazz_id);

        resp.getWriter().write(teacherService.delClass(req.getSession(), clazz));
    }

    /**
     * 获取班级信息列表
     * 地址：/manage?action=clazzList
     */
    public void clazzList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TeacherService teacherService = (TeacherService) new TeacherServiceProxy(req.getSession(),
                new TeacherServiceImpl()).getProxy();
        resp.getWriter().write(teacherService.selectClass(req.getSession()));
    }

}