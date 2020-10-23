package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.service.UserService;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.service.user.StudentService;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.MD5;

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
        jsonPack.put("event", 0);
        jsonPack.put("info", o);
        return jsonPack.toJson();
    }

    @Override
    public String changePassword(HttpSession session, String old, String password) {
        Student student = (Student) session.getAttribute("user");
        JsonPack jsonPack = new JsonPack();
        String oldPassword = MD5.MD5Encrypt(old);
        String newPassword = MD5.MD5Encrypt(password);
        assert oldPassword != null;
        if (!oldPassword.equals(student.getPassword())){
            jsonPack.put("event", 2);
            jsonPack.put("msg", "旧密码错误！");
            return jsonPack.toJson();
        }
        student.setPassword(newPassword);
        int modify = studentDao.modify(student);
        if (modify == 0) {
            jsonPack.put("event", 0);
            jsonPack.put("msg", "修改密码成功！");
            return jsonPack.toJson();
        }
        jsonPack.put("event", 3);
        jsonPack.put("msg", "修改密码失败，未知错误！");
        return jsonPack.toJson();
    }
}
