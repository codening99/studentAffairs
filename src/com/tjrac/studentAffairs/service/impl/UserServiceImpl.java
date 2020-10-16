package com.tjrac.studentAffairs.service.impl;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.utils.CompPara;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.MD5;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZeNing
 * @create 2020-10-15 20:48
 */
public class UserServiceImpl implements UserService {

    //存放user的服务器
    final static List<HttpSession> listSession = new ArrayList<>();

    BaseDao<Student> studentDao = new BaseDao<>(Student.class);
    BaseDao<Teacher> teacherDao = new BaseDao<>(Teacher.class);

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
                        if (s.getId().equals(session.getId())) {
                            //避免重复登陆
                            listSession.remove(s);
                            //注销
                            s.invalidate();
                        }
                    }

                    json.put("event", 0);
                    json.put("msg", "登陆成功!");

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
                        if (s.getId().equals(session.getId())) {
                            //避免重复登陆
                            listSession.remove(s);
                            //注销
                            s.invalidate();
                        }
                    }

                    json.put("event", 0);
                    json.put("msg", "登陆成功!");

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
     *
     * @param session 浏览器回话对象
     * @param object  对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String addObject(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user");

        int competence = getCompetence(adminObj);

        JsonPack json = new JsonPack();

        //判断权限
        if (competence == 1) { //如果存在1权限

            if (object instanceof Teacher) {  //要添加的为老师

                //判断数据库是否已经有同账户名的老师
                Object teacherDB = teacherDao.query("account", ((Teacher) adminObj).getAccount());

                if (teacherDB != null) { //数据库中已存在该账户
                    json.put("event", 2);
                    json.put("msg", "账户已存在");
                } else {
                    teacherDao.insert((Teacher) adminObj); //存入数据库
                    json.put("event", 0);
                    json.put("msg", "添加成功");
                }
            } else {

                //判断数据库是否已经有同学号的学生
                Object teacherDB = studentDao.query("sno", ((Student) adminObj).getSno());

                if (teacherDB != null) { //数据库中已存在该账户
                    json.put("event", 2);
                    json.put("msg", "学号已存在");
                } else {
                    studentDao.insert((Student) adminObj); //存入数据库
                    json.put("event", 0);
                    json.put("msg", "添加成功");
                }
            }

        } else if (competence == 2) { //没有添加的权限
            json.put("event", 1);
            json.put("msg", "权限不足");
        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }

        return json.toJson();
    }

    @Override
    public String modifyObject(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user"); //获取user
        JsonPack json = new JsonPack();

        int competence = getCompetence(adminObj); //获取权限

        if (competence == 1) { //有修改的权限

            if (object instanceof Teacher) { //要修改的是teacher

                Teacher teacherDB = teacherDao.query("account", ((Teacher) object).getAccount()); //数据库中寻找该对象

                if (teacherDB == null) { //没有该账户 无法更新
                    json.put("event", 2);
                    json.put("msg", "没有该账户,无法修改");
                } else { //找到该账户
                    teacherDao.modify((Teacher) object); //修改
                    json.put("event", 0);
                    json.put("msg", "修改成功");
                }

            } else if (object instanceof Student) { //要修改的是student

                Student studentDB = studentDao.query("sno", ((Student) object).getSno());

                if (studentDB == null) { //没有该账户 无法更新
                    json.put("event", 2);
                    json.put("msg", "没有该学生,无法修改");
                } else { //找到该账户
                    studentDao.modify((Student) object); //修改
                    json.put("event", 0);
                    json.put("msg", "修改成功");
                }

            }

        } else if (competence == 2) {
            json.put("event", 1);
            json.put("msg", "权限不足");
        } else {
            json.put("event", 1);
            json.put("msg", "获取权限失败");
        }

        return json.toJson();
    }

    @Override
    public String delObject(HttpSession session, Object object) {

        Object adminObj = session.getAttribute("user"); //获取user
        JsonPack json = new JsonPack();

        int competence = getCompetence(adminObj); //获取权限

        if (competence == 1) { //有删除权限

            if (object instanceof Teacher) { //要删除的是老师

                Teacher teacherDB = teacherDao.query("account", ((Teacher) object).getAccount());

                if (teacherDB == null) { //数据库中没有该老师账户

                    json.put("event", 2);
                    json.put("msg", "没有找到账户信息，删除失败");

                } else { //找到该账户信息

                    teacherDao.delete((Teacher) object); //删除该账户信息

                    json.put("event", 0);
                    json.put("msg", "删除成功");

                }

            } else if (object instanceof Student) {

                Student studentDB = studentDao.query("sno", ((Student) object).getSno());

                if (studentDB == null) { //没有找到该学生信息

                    json.put("event", 2);
                    json.put("msg", "没有找到学生信息，删除失败");

                } else {

                    studentDao.delete((Student) object); //删除该学生

                    json.put("event", 0);
                    json.put("msg", "删除成功");

                }

            } else {
                json.put("event", 1);
                json.put("msg", "获取权限失败");
            }

        } else if (competence == 2) {
            json.put("event", 1);
            json.put("msg", "权限不足");
        }

        return json.toJson();
    }

    @Override
    public String selectObject(HttpSession session, Class<?> c) {



        return null;
    }

    /**
     * 判断是否有1权限
     *
     * @return 1有该权限 2没有该权限 3获取权限失败
     */
    private int getCompetence(Object obj) {

        int competence;
        //无论是学生还是老师，都获取他的权限
        try {
            Method getCompetence_id = obj.getClass().getDeclaredMethod("getCompetence_id");
            competence = (int) getCompetence_id.invoke(obj); //获取到的权限大小
        } catch (Exception e) {
            System.out.println("获取权限失败");
            return 3;
        }

        //获取权限
        CompPara compPara = new CompPara(competence);
        if (compPara.test(1)) {
            return 1;
        } else {
            return 0;
        }

    }
}
