package com.tjrac.studentAffairs.service.impl;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.MD5;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ZeNing
 * @create 2020-10-15 20:48
 */
public class UserServiceImpl implements UserService {

    /**
     * 学生端和教师端的登录接口
     *
     * @param session  浏览器回话对象
     * @param type     类型（1：教师登录  2：学生登录）
     * @param account  账号
     * @param password 密码
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String login(HttpSession session, Integer type, String account, String password) {

        //将密码加密
        String newPassword = MD5.MD5Encrypt(password);
        //获取json
        JsonPack json = new JsonPack();

        //创建存放user的服务器
        ServletContext servletContext = session.getServletContext();
        Object objOnlineContainer = servletContext.getAttribute("onlineContainer");
        Map<String, HttpSession> onlineContainer;

        if (objOnlineContainer == null) {
            onlineContainer = new HashMap<>();
            servletContext.setAttribute("onlineContainer", onlineContainer);
        } else {
            onlineContainer = (Map<String, HttpSession>) objOnlineContainer;
        }

        //教师登录
        if (type == 1) {

            //获取dao层
            BaseDao<Teacher> teacherBaseDao = new BaseDao<>(Teacher.class);

            //查询
            Teacher teacher = teacherBaseDao.query("account", account);

            if (teacher != null) { //查询到该账户

                if (teacher.getPassword().equals(newPassword)) { //判断密码是否一样
                    json.put("teacher", teacher);
                    json.put("event", 0);
                    json.put("msg", "登陆成功!");

                    //保存
                    session.setAttribute("user", teacher);
                    onlineContainer.put(session.getId(), session);

                } else { //密码错误
                    json.put("event", 2);
                    json.put("msg", "密码错误!");
                }

            } else { //未找到该账号
                json.put("event", 1);
                json.put("msg", "用户名不存在!");
            }

        } else if (type == 2) { //学生登录

            //获取dao层
            BaseDao<Student> studentDao = new BaseDao<>(Student.class);

            //查询
            Student student = studentDao.query("sno", account);

            if (student != null) { //查询到该账户

                if (student.getPassword().equals(newPassword)) { //判断密码是否一样
                    json.put("student", student);
                    json.put("event", 0);
                    json.put("msg", "登陆成功!");

                    //保存
                    session.setAttribute("user", student);
                    onlineContainer.put(session.getId(), session);
                } else { //密码错误
                    json.put("event", 2);
                    json.put("msg", "密码错误!");
                }

            } else { //未找到该账号
                json.put("event", 1);
                json.put("msg", "用户名不存在!");
            }

        } else {
            json.put("mag", "type错误");
        }
        return json.toJson();
    }

    /**
     * 返回在线状态信息
     *
     * @param session 浏览器回话对象
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String getOnlineStatus(HttpSession session) {
        JsonPack json = new JsonPack();
        Object user = session.getAttribute("user");

        Map<String, HttpSession> onlineContainer = (Map<String, HttpSession>) session.getAttribute("onlineContainer");
        Iterator<Map.Entry<String, HttpSession>> iterator = onlineContainer.entrySet().iterator();
        while (iterator.hasNext()) {

            Map.Entry<String, HttpSession> next = iterator.next();
            HttpSession sessionChild = next.getValue();
            Object onlineUser = sessionChild.getAttribute("user");

            if (session == sessionChild)
                break;

            if (user instanceof Student) {
                if (onlineUser instanceof Student) {
                    if (((Student) onlineUser).getSno().equals(((Student) user).getSno())) {
                        json.put("event", 1);
                        json.put("msg", "在线中");
                    }
                }
            } else if (user instanceof Teacher) {
                if (onlineUser instanceof Teacher) {
                    if (((Teacher) user).getAccount().equals(((Teacher) onlineUser).getAccount())) {
                        json.put("event", 1);
                        json.put("msg", "在线中");
                    }
                }
            }


        }
        json.put("event", 0);
        json.put("msg", "未登录");
        return json.toJson();
    }

    /**
     * 注销登录
     *
     * @param session 浏览器回话对象
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String logout(HttpSession session) {
        return null;
    }

    @Override
    public String addObject(HttpSession session, Object object) {
        return null;
    }

    @Override
    public String modifyObject(HttpSession session, Object object) {
        return null;
    }

    @Override
    public String delObject(HttpSession session, Object object) {
        return null;
    }

    @Override
    public String selectObject(HttpSession session, Class<?> c) {
        return null;
    }
}