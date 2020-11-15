package com.tjrac.studentAffairs.service.user.impl;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.student.Department;
import com.tjrac.studentAffairs.domain.student.Grade;
import com.tjrac.studentAffairs.domain.student.Specialty;
import com.tjrac.studentAffairs.domain.user.Teacher;
import com.tjrac.studentAffairs.service.user.ManagerService;
import com.tjrac.studentAffairs.utils.JsonPack;
import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * ManagerServiceImpl 管理员业务层实现类
 *
 * @author : xziying
 * @create : 2020-10-23 18:15
 */
public class ManagerServiceImpl extends TeacherServiceImpl implements ManagerService {
    public final TypeInfo typeInfo = null;
    private final BaseDao<Grade> gradeDao = new BaseDao<>(Grade.class);
    private final BaseDao<Department> departmentBaseDao = new BaseDao<>(Department.class);
    private final BaseDao<Specialty> specialtyBaseDao = new BaseDao<>(Specialty.class);

    /**
     * 老师
     */
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
        System.out.println();
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

    /**
     * 年级
     */
    @Override
    public String addGrade(HttpSession session, Grade grade) {
        JsonPack json = new JsonPack();
        int insert = gradeDao.insert(grade);
        if (insert == 1062) {
            json.put("event", 2);
            json.put("msg", "年级已存在");
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
    public String modifyGrade(HttpSession session, Grade grade) {
        JsonPack json = new JsonPack();
        int modify = gradeDao.modify(grade); //修改
        if (modify == 404) {
            json.put("event", 2);
            json.put("msg", "没有该年级,无法修改");
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
    public String delGrade(HttpSession session, Grade grade) {
        JsonPack json = new JsonPack();
        int delete = gradeDao.delete(grade);//删除该账户信息
        if (delete == 0) {
            json.put("event", 0);
            json.put("msg", "删除成功");
        } else if (delete == 404){
            json.put("event", 2);
            json.put("msg", "没有找到年级信息，删除失败");
        } else {
            json.put("event", 3);
            json.put("msg", "删除失败");
        }
        return json.toJson();
    }

    @Override
    public String selectGrade(HttpSession session) {
        JsonPack json = new JsonPack();
        List<Grade> grades = gradeDao.queryList();

        json.put("event", 0);
        json.put("count", grades.size());
        json.put("grades", grades);
        return json.toJson();
    }

    /**
     * 系
     */
    @Override
    public String addDepartment(HttpSession session, Department department) {
        JsonPack json = new JsonPack();
        int insert = departmentBaseDao.insert(department);
        if (insert == 1062) {
            json.put("event", 2);
            json.put("msg", "该系已存在");
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
    public String modifyDepartment(HttpSession session, Department department) {
        JsonPack json = new JsonPack();
        int modify = departmentBaseDao.modify(department); //修改
        if (modify == 404) {
            json.put("event", 2);
            json.put("msg", "没有该系别,无法修改");
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
    public String delDepartment(HttpSession session, Department department) {
        JsonPack json = new JsonPack();
        int delete = departmentBaseDao.delete(department);//删除该账户信息
        if (delete == 0) {
            json.put("event", 0);
            json.put("msg", "删除成功");
        } else if (delete == 404){
            json.put("event", 2);
            json.put("msg", "没有找到系别信息，删除失败");
        } else {
            json.put("event", 3);
            json.put("msg", "删除失败");
        }
        return json.toJson();
    }

    @Override
    public String selectDepartment(HttpSession session) {
        JsonPack json = new JsonPack();
        List<Department> departments = departmentBaseDao.queryList();

        json.put("event", 0);
        json.put("count", departments.size());
        json.put("departments", departments);
        return json.toJson();
    }

    /**
     * 专业
     */
    @Override
    public String addSpecialty(HttpSession session, Specialty specialty) {
        JsonPack json = new JsonPack();
        int insert = specialtyBaseDao.insert(specialty);
        if (insert == 1062) {
            json.put("event", 2);
            json.put("msg", "该专业已存在");
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
    public String modifySpecialty(HttpSession session, Specialty specialty) {
        JsonPack json = new JsonPack();
        int modify = specialtyBaseDao.modify(specialty); //修改
        if (modify == 404) {
            json.put("event", 2);
            json.put("msg", "没有该专业,无法修改");
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
    public String delSpecialty(HttpSession session, Specialty specialty) {
        JsonPack json = new JsonPack();
        int delete = specialtyBaseDao.delete(specialty);//删除该账户信息
        if (delete == 0) {
            json.put("event", 0);
            json.put("msg", "删除成功");
        } else if (delete == 404){
            json.put("event", 2);
            json.put("msg", "没有找到专业信息，删除失败");
        } else {
            json.put("event", 3);
            json.put("msg", "删除失败");
        }
        return json.toJson();
    }

    @Override
    public String selectSpecialty(HttpSession session) {
        JsonPack json = new JsonPack();
        List<Specialty> specialties = specialtyBaseDao.queryList();

        json.put("event", 0);
        json.put("count", specialties.size());
        json.put("specialties", specialties);
        return json.toJson();
    }

    @Test
    public void test() {
//        System.out.println(addGrade(null, ));
        Grade grade = new Grade("grade");
        System.out.println(selectGrade(null));
    }

}
