package com.tjrac.studentAffairs.service.user.proxy;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TeacherServiceProxy 老师业务的代理类
 *
 * @author : xziying
 * @create : 2020-10-24 10:06
 */
public class TeacherServiceProxy extends UserServiceProxy<TeacherService> {

    public TeacherServiceProxy(HttpSession session, TeacherService userService) {
        super(session, userService);
    }
    /**
     * 对浏览器回话对象的用户进行全面检查 即可通过
     * @return 通过返回null 失败返回json文本
     */
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
        if (!(user instanceof Teacher)){
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 2);
            jsonPack.put("msg", "接口错误！");
            return jsonPack.toJson();
        }
        Teacher teacher = (Teacher) user;
        Competence competence = new Competence(teacher.getCompetence());
        if (!competence.test(competence.COMP_TEACHER)){
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 2);
            jsonPack.put("msg", "权限不足！");
            return jsonPack.toJson();
        }
        return null;
    }
}
