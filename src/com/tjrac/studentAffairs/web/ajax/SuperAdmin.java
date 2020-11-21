package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.domain.student.Department;
import com.tjrac.studentAffairs.domain.student.Grade;
import com.tjrac.studentAffairs.domain.student.Specialty;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.user.ManagerService;
import com.tjrac.studentAffairs.service.user.impl.ManagerServiceImpl;
import com.tjrac.studentAffairs.service.user.proxy.ManagerServiceProxy;
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
 * @author ZeNing
 * @create 2020-11-15 15:42
 */
@WebServlet("/superAdmin")
public class SuperAdmin extends BaseServlet {

    /**
     * 获取老师信息列表
     * 地址：/superAdmin?action=teacherList
     */
    public void teacherList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        resp.getWriter().write(managerService.selectTeacher(req.getSession()));
    }


    /**
     * 新增老师信息
     * 地址：/superAdmin?action=addTeacher
     */
    public void addTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        String account = req.getParameter("account");

        if (managerService.selectTeacherByAccount(account) == null) {
            String password = req.getParameter("password");
            if ("".equals(password)) {
                password = "123456"; //默认为123456
            }
            String newPassword = MD5.MD5Encrypt(password);
            String name = req.getParameter("name");
            String teacher_sex = req.getParameter("teacher_sex");
            Teacher teacher = new Teacher(account, newPassword, name, teacher_sex, Competence.COMP_TEACHER);
            resp.getWriter().write(managerService.addUser(req.getSession(), teacher));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "老师账户已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }

    }


    /**
     * 修改老师信息
     * 地址：/superAdmin?action=updateTeacher
     */
    public void updateTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        Integer teacher_id = null;
        try {
            teacher_id = Integer.parseInt(req.getParameter("teacher_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String newPassword = MD5.MD5Encrypt(password);
        String name = req.getParameter("name");
        String teacher_sex = req.getParameter("teacher_sex");

        Teacher teacher = new Teacher(teacher_id, account, newPassword, name,
                teacher_sex, Competence.COMP_TEACHER);

        resp.getWriter().write(managerService.modifyUser(req.getSession(), teacher));
    }

    /**
     * 删除老师信息
     * 地址：/superAdmin?action=deleteTeacher
     */
    public void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        Integer teacher_id = null;
        try {
            teacher_id = Integer.valueOf(req.getParameter("teacher_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        resp.getWriter().write(managerService.delUser(req.getSession(), new Teacher(teacher_id)));
    }

    /**
     * 获取年级信息列表
     * 地址：/superAdmin?action=gradeList
     */
    public void gradeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        resp.getWriter().write(managerService.selectGrade(req.getSession()));
    }

    /**
     * 新增年级信息
     * 地址：/superAdmin?action=addGrade
     */
    public void addGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        String grade_name = req.getParameter("grade_name");
        if (managerService.selectGradeByGradeName(grade_name) == null) {
            resp.getWriter().write(managerService.addGrade(req.getSession(), new Grade(grade_name)));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "年级已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }

    }

    /**
     * 修改年级信息
     * 地址：/superAdmin?action=updateGrade
     */
    public void updateGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        Integer grade_id = null;
        try {
            grade_id = Integer.parseInt(req.getParameter("grade_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String grade_name = req.getParameter("grade_name");

        Grade grade = new Grade(grade_id, grade_name);

        resp.getWriter().write(managerService.modifyGrade(req.getSession(), grade));
    }

    /**
     * 删除年级信息
     * 地址：/superAdmin?action=deleteGrade
     */
    public void deleteGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        Integer grade_id = null;
        try {
            grade_id = Integer.valueOf(req.getParameter("grade_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        resp.getWriter().write(managerService.delGrade(req.getSession(), new Grade(grade_id)));
    }

    /**
     * 获取系别信息列表
     * 地址：/superAdmin?action=departmentList
     */
    public void departmentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        resp.getWriter().write(managerService.selectDepartment(req.getSession()));
    }

    /**
     * 新增系别信息
     * 地址：/superAdmin?action=addDepartment
     */
    public void addDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        String department_name = req.getParameter("department_name");

        if (managerService.selectDepartmentByDepartmentName(department_name) == null) {
            Department department = new Department(department_name);
            resp.getWriter().write(managerService.addDepartment(req.getSession(), department));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "系别已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }

    }

    /**
     * 修改系别信息
     * 地址：/superAdmin?action=updateDepartment
     */
    public void updateDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        Integer department_id = null;
        try {
            department_id = Integer.parseInt(req.getParameter("department_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String department_name = req.getParameter("department_name");

        Department department = new Department(department_id, department_name);

        resp.getWriter().write(managerService.modifyDepartment(req.getSession(), department));
    }

    /**
     * 删除系别信息
     * 地址：/superAdmin?action=deleteDepartment
     */
    public void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        Integer department_id = null;
        try {
            department_id = Integer.valueOf(req.getParameter("department_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        resp.getWriter().write(managerService.delDepartment(req.getSession(), new Department(department_id)));
    }

    /**
     * 获取专业信息列表
     * 地址：/superAdmin?action=specialtyList
     */
    public void specialtyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        resp.getWriter().write(managerService.selectSpecialty(req.getSession()));
    }

    /**
     * 新增专业
     * 地址：/superAdmin?action=addSpecialty
     */
    public void addSpecialty(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        String specialty_name = req.getParameter("specialty_name");
        if (managerService.selectSpecialtyBySpecialtyName(specialty_name) == null) {
            Integer department_id = Integer.getInteger(req.getParameter("department_id"));
            Specialty specialty = new Specialty(department_id, specialty_name);
            resp.getWriter().write(managerService.addSpecialty(req.getSession(), specialty));
        } else {
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 3);
            jsonPack.put("msg", "专业已存在，加入失败");
            resp.getWriter().write(jsonPack.toJson());
        }
    }

    /**
     * 修改专业信息
     * 地址：/superAdmin?action=updateSpecialty
     */
    public void updateSpecialty(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();

        Integer specialty_id = null;
        Integer department_id = null;
        try {
            specialty_id = Integer.parseInt(req.getParameter("specialty_id"));
            department_id = Integer.parseInt(req.getParameter("department_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String specialty_name = req.getParameter("specialty_name");

        Specialty specialty = new Specialty(specialty_id, department_id, specialty_name);

        resp.getWriter().write(managerService.modifySpecialty(req.getSession(), specialty));
    }

    /**
     * 删除专业信息
     * 地址：/superAdmin?action=delSpecialty
     */
    public void delSpecialty(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ManagerService managerService = (ManagerService) new ManagerServiceProxy(req.getSession(), new ManagerServiceImpl()).getProxy();
        Integer specialty_id = null;
        try {
            specialty_id = Integer.parseInt(req.getParameter("specialty_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Specialty specialty = new Specialty(specialty_id);

        resp.getWriter().write(managerService.delSpecialty(req.getSession(), specialty));
    }

}
