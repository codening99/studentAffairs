package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.service.user.ManagerService;
import com.tjrac.studentAffairs.service.user.impl.ManagerServiceImpl;
import com.tjrac.studentAffairs.service.user.proxy.ManagerServiceProxy;
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

}
