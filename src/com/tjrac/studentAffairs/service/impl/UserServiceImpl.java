package com.tjrac.studentAffairs.service.impl;

import com.tjrac.studentAffairs.dao.basedao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.MD5;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ZeNing
 * @create 2020-10-15 20:48
 */
public class UserServiceImpl implements UserService {

    //存放user的服务器
    protected final static List<HttpSession> listSession = new CopyOnWriteArrayList<>();

    protected BaseDao<Student> studentDao = new BaseDao<>(Student.class);
    protected BaseDao<Teacher> teacherDao = new BaseDao<>(Teacher.class);

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

            //查询
            Teacher teacher = teacherDao.query("account", account);

            if (teacher != null) { //查询到该账户

                if (teacher.getPassword().equals(newPassword)) { //判断密码是否一样

                    for (HttpSession s : listSession) {
                        //服务器中有session
                        try {
                            Object user = s.getAttribute("user");
                            if (user instanceof Teacher) {
                                if (((Teacher) user).getAccount().equals(account)) {
                                    //避免重复登陆
                                    listSession.remove(s);
                                    //注销
                                    if (session != s)
                                        s.invalidate();
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            listSession.remove(s);
                        }
                    }

                    json.put("event", 0);
                    json.put("msg", "登陆成功!");
                    json.put("teacher", teacher);

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

            //查询
            Student student = studentDao.query("sno", account);

            if (student != null) { //查询到该账户

                if (student.getPassword().equals(newPassword)) { //判断密码是否一样

                    for (HttpSession s : listSession) {
                        //服务器中有session
                        try {
                            Object user = s.getAttribute("user");
                            if (user instanceof Student) {
                                if (((Student) user).getSno().equals(account)) {
                                    //避免重复登陆
                                    listSession.remove(s);
                                    //注销
                                    if (session != s)
                                        s.invalidate();
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            listSession.remove(s);
                        }
                    }

                    json.put("event", 0);
                    json.put("msg", "登陆成功!");
                    json.put("student", student);

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

        Object user = session.getAttribute("user");

        if (user != null) {

            if (user instanceof Teacher) {
                json.put("event", 1);
                json.put("msg", "在线中!");
                json.put("type", 1);
            } else {
                json.put("event", 1);
                json.put("msg", "在线中!");
                json.put("type", 2);
            }

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
     * 判断是否有1权限
     *
     * @return 1有该权限 2没有该权限 3获取权限失败
     */
    protected Competence getCompetence(Object obj) {

        int competence;
        //无论是学生还是老师，都获取他的权限
        try {
            Method getCompetence_id = obj.getClass().getDeclaredMethod("getCompetence_id");
            competence = (int) getCompetence_id.invoke(obj); //获取到的权限大小
        } catch (Exception e) {
            System.out.println("获取权限失败");
            return null;
        }
        //获取权限
        return new Competence(competence);
    }
}
