package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.service.user.impl.StudentServiceImpl;
import com.tjrac.studentAffairs.service.user.proxy.StudentServiceProxy;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.rmi.ServerException;

/**
 * Student 学生AJAX接口
 *
 * @author : xziying
 * @create : 2020-10-23 17:17
 */
@WebServlet("/student")
public class Student extends BaseServlet {

    /**
     * 获取学生信息
     * 地址：student?action=getStudentInfo
     */
    public void getStudentInfo(HttpServletRequest req,HttpServletResponse resp)throws ServerException,IOException{
        StudentService studentService = (StudentService) new StudentServiceProxy(req.getSession(),
                new StudentServiceImpl()).getProxy();
        resp.getWriter().write(studentService.getStudentInfo(req.getSession()));
    }

    /**
     * 修改密码
     * 地址：student?action=changePassword
     * 参数：old password
     */
    public void changePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        StudentService studentService = (StudentService) new StudentServiceProxy(req.getSession(),
                new StudentServiceImpl()).getProxy();
        String old = req.getParameter("old");
        String password = req.getParameter("password");
        resp.getWriter().write(studentService.changePassword(req.getSession(), old, password));
    }
}
