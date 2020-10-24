package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.user.ManagerService;
import com.tjrac.studentAffairs.utils.JsonPack;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ManagerServiceImpl 管理员业务层实现类
 *
 * @author : xziying
 * @create : 2020-10-23 18:15
 */
public class ManagerServiceImpl extends TeacherServiceImpl implements ManagerService {

    @Override
    public String addUser(HttpSession session, Teacher user) {
        JsonPack json = new JsonPack();
        int insert = teacherDao.insert((Teacher) user);//存入数据库
        if (insert == 1062){
            json.put("event", 2);
            json.put("msg", "账户已存在");
        } else if (insert == 0) {
            json.put("event", 0);
            json.put("msg", "添加成功");
        } else {
            json.put("event", 3);
            json.put("msg", "添加失败");
        }
        return json.toJson();
    }

    @Override
    public String modifyUser(HttpSession session, Teacher user) {
        JsonPack json = new JsonPack();
        int modify = teacherDao.modify((Teacher) user); //修改
        if (modify == 404) {
            json.put("event", 2);
            json.put("msg", "没有该账户,无法修改");
        } else if (modify == 0) {
            json.put("event", 0);
            json.put("msg", "修改成功");
        } else {
            json.put("event", 3);
            json.put("msg", "修改失败");
        }
        return json.toJson();
    }

    @Override
    public String delUser(HttpSession session, Teacher user) {
        JsonPack json = new JsonPack();
        int delete = teacherDao.delete((Teacher) user);//删除该账户信息
        if (delete == 0) {
            json.put("event", 0);
            json.put("msg", "删除成功");
        } else if (delete == 404){
            json.put("event", 2);
            json.put("msg", "没有找到账户信息，删除失败");
        } else {
            json.put("event", 3);
            json.put("msg", "删除失败");
        }
        return json.toJson();
    }

    @Override
    public String selectTeacher(HttpSession session) {
        JsonPack json = new JsonPack();
        List<Teacher> teachers = teacherDao.queryList();

        json.put("event", 0);
        json.put("count", teachers.size());
        json.put("teachers", teachers);
        return json.toJson();
    }
}
