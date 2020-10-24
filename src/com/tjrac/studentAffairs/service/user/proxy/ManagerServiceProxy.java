package com.tjrac.studentAffairs.service.user.proxy;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.user.ManagerService;
import com.tjrac.studentAffairs.utils.Competence;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;

/**
 * ManagerServiceProxy 管理员代理类实现类
 *
 * @author : xziying
 * @create : 2020-10-24 10:19
 */
public class ManagerServiceProxy extends UserServiceProxy<ManagerService>{
    public ManagerServiceProxy(HttpSession session, ManagerService userService) {
        super(session, userService);
    }

    @Override
    protected String checkpoint() {
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
        if (!competence.test(competence.COMP_MANAGER)){
            JsonPack jsonPack = new JsonPack();
            jsonPack.put("event", 2);
            jsonPack.put("msg", "权限不足！");
            return jsonPack.toJson();
        }
        return null;
    }
}
