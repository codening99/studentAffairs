package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * login 登录相关请求
 *
 * @author : xziying
 * @create : 2020-10-16 12:23
 */
public class Login extends BaseServlet {
    /**
     * 登录
     * 地址：login?action=login
     * 参数：type account password
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        int type;
        try {
            type = Integer.parseInt(req.getParameter("type"));
        } catch (NumberFormatException e){
            JsonPack j  = new JsonPack();
            j.put("event", -1);
            j.put("msg", "类型错误");
            resp.getWriter().write(j.toJson());
            return;
        }
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        resp.getWriter().write(userService.login(req.getSession(), type, account, password));
    }
}
