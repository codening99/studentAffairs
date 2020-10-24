package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.impl.UserServiceImpl;
import com.tjrac.studentAffairs.service.user.TeacherService;
import com.tjrac.studentAffairs.utils.JsonPack;
import java.util.List;
import org.junit.Test;

/**
 * TeacherServiceImpl 老师业务层实现类
 *
 * @author : xziying
 * @create : 2020-10-23 17:40
 */
public class TeacherServiceImpl extends UserServiceImpl implements TeacherService {
    /**
     * 增删查改 先检测是否拥有1(管理员权限)权限的账号进行
     *
     * @param user  对象信息
     * @return 返回Json字符串，参考README.md的文件的ajax请求接口
     */
    @Override
    public String addUser(Student user) {
        JsonPack json = new JsonPack();
        int insert = studentDao.insert(user); //存入数据库;//存入数据库
        if (insert == 1062){
            json.put("event", 2);
            json.put("msg", "学号已存在");
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
    public String modifyUser(Student user) {
        JsonPack json = new JsonPack();
        int modify = studentDao.modify(user);//修改
        if (modify == 404) {
            json.put("event", 2);
            json.put("msg", "没有该学号,无法修改");
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
    public String delUser(Student user) {
        JsonPack json = new JsonPack();
        int delete = studentDao.delete(user);//删除该学生
        if (delete == 0) {
            json.put("event", 0);
            json.put("msg", "删除成功");
        } else if (delete == 404){
            json.put("event", 2);
            json.put("msg", "没有找到学生信息，删除失败");
        } else {
            json.put("event", 3);
            json.put("msg", "删除失败");
        }
        return json.toJson();
    }

    @Override
    public String selectStudent() {
        JsonPack json = new JsonPack();
        List<Student> students = studentDao.queryList();
        json.put("event", 0);
        json.put("count", students.size());
        json.put("students", students);
        return json.toJson();
    }

    @Test
    public void test(){
        TeacherService teacherService= new TeacherServiceImpl();
        Teacher teacher = new Teacher(
                2,
                "test123",
                "123",
                "王老师",
                "男",
                1
        );

    }
}
