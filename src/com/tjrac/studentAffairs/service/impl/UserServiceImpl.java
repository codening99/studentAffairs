package com.tjrac.studentAffairs.service.impl;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.MD5;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZeNing
 * @create 2020-10-15 20:48
 */
public class UserServiceImpl implements UserService {

    //存放user的服务器
    final static List<HttpSession> listSession = new ArrayList<>();

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

                    for (HttpSession s : listSession) {
                        //服务器中有session
                        if (s.getId().equals(session.getId())) {
                            //避免重复登陆
                            listSession.remove(s);
                            //注销
                            s.invalidate();
                        }
                    }

                    //保存
                    session.setAttribute("user", teacher);
                    listSession.add(session);

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

                    for (HttpSession s : listSession) {
                        //服务器中有session
                        if (s.getId().equals(session.getId())) {
                            //避免重复登陆
                            listSession.remove(s);
                            //注销
                            s.invalidate();
                        }
                    }

                    //保存
                    session.setAttribute("user", student);
                    listSession.add(session);

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

        if (session.getAttribute("user") != null) {
            json.put("event", 1);
            json.put("msg", "在线中!");
        } else {
            json.put("event", 0);
            json.put("msg", "未登录!");
        }

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

        JsonPack json = new JsonPack();

        for (HttpSession s : listSession) {
            if (s.getId().equals(session.getId())) {
                s.invalidate();
                listSession.remove(s);
                json.put("event", 0);
                json.put("msg", "注销成功");
            }
        }

        json.put("event", 1);
        json.put("msg", "注销失败");
        return json.toJson();
    }

    /**
     * 增删查改 先检测是否拥有1(管理员权限)权限的账号进行
     * @param session 浏览器回话对象
     * @param object   对象信息
     * @return  返回Json字符串，参考README.md的文件的ajax请求接口
     */
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
