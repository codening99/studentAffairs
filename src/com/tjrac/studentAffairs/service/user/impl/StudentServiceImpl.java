package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;

/**
 * StudentServiceImpl 学生业务实现类
 *
 * @author : xziying
 * @create : 2020-10-23 17:32
 */
public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    @Override
    public String getStudentInfo(HttpSession session) {
        Object o = session.getAttribute("user");
        JsonPack jsonPack = new JsonPack();
        if (o == null){
            jsonPack.put("event", 1);
            jsonPack.put("msg", "未登录！");
            return jsonPack.toJson();
        }
        if (!(o instanceof Student)){
            jsonPack.put("event", 2);
            jsonPack.put("msg", "接口错误！");
            return jsonPack.toJson();
        }
        jsonPack.put("event", 0);
        jsonPack.put("info", o);
        return jsonPack.toJson();
    }
}
