package com.tjrac.studentAffairs.service.user.proxy;


import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * StudentServiceProxy 学生业务的代理类
 *
 * 对所有方法权限管理
 *
 * @author : xziying
 * @create : 2020-10-23 22:07
 */
public class StudentServiceProxy extends UserServiceProxy<StudentService>{

    public StudentServiceProxy(HttpSession session, StudentService userService) {
        super(session, userService);
    }

    @Override
    protected String checkpoint(Method method) {
        Object user = session.getAttribute("user");
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
        return null;
    }
}
