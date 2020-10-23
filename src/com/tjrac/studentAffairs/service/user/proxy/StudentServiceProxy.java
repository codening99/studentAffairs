package com.tjrac.studentAffairs.service.user.proxy;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.service.user.impl.StudentServiceImpl;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * StudentServiceProxy 学生业务的代理类
 *
 * 对所有方法权限管理
 *
 * @author : xziying
 * @create : 2020-10-23 22:07
 */
public class StudentServiceProxy implements InvocationHandler{
    HttpSession session;
    StudentService studentService;

    public StudentServiceProxy(HttpSession session, StudentService studentService) {
        this.session = session;
        this.studentService = studentService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object user = session.getAttribute("user");
        {
            // 检测登录状态
            if (user == null) {
                JsonPack jsonPack = new JsonPack();
                jsonPack.put("event", 1);
                jsonPack.put("msg", "未登录");
                return jsonPack.toJson();
            }
            if (!(user instanceof Student)){
                JsonPack jsonPack = new JsonPack();
                jsonPack.put("event", 2);
                jsonPack.put("msg", "接口错误！");
                return jsonPack.toJson();
            }
        }
        return method.invoke(studentService, args);
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(studentService.getClass().getClassLoader(), studentService.getClass().getInterfaces(), this);
    }
}
