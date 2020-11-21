package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.dao.ChooseDao;
import com.tjrac.studentAffairs.dao.impl.ChooseDaoImpl;
import com.tjrac.studentAffairs.domain.config.Choose;
import com.tjrac.studentAffairs.domain.user.Student;
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

    ChooseDao chooseDao = new ChooseDaoImpl();

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
        if (!oldPassword.equals(student.getPassword())) {
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

    @Override
    public Student selectStudentBySno(String sno) {
        return studentDao.query("student_id", sno);
    }

    @Override
    public String queryChooserByGid(HttpSession session) {

        JsonPack json = new JsonPack();

        Student student = (Student) session.getAttribute("user");
        Integer grade_id = student.getGrade_id(); //年级
        Choose choose = chooseDao.queryChooseByGrade(grade_id);

        if (choose == null) { //未开启选方向
            json.put("event", 0);
            json.put("msg", "未开启选方向");
        } else {
            if (choose.getStatus() == 0) { //选方向已过期
                json.put("event", 1);
                json.put("msg", "本次选方向已结束");
            } else {
                json.put("event", 2);
                json.put("msg", "正在进行选方向");
            }
        }
        return json.toJson();
    }
}
