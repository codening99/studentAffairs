package com.tjrac.studentAffairs.test;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.domain.user.Teacher;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ZeNing
 * @create 2020-10-16 20:48
 */
public class TestMethod {

    @Test
    public void test() {

        Teacher teacher = new Teacher();
        teacher.setCompetence_id(1);

        try {
            Method getCompetence_id = Teacher.class.getDeclaredMethod("getCompetence_id");
            System.out.println(getCompetence_id.invoke(teacher));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {

        Object o = selectObject(new Student().getClass());

        System.out.println(o);

    }

    public Object selectObject(Class<?> c) {
        Object o = null;
        try {
            o = c.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return o;
    }
    @Test
    public void tesst5() {

        Student student = new Student(10,"6018203050","张飞", "123456" , "男",4,
                "2018级","软件工程1班","计算机科学与技术","计算机科学与技术",
                null);

        System.out.println(student);
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);

        System.out.println(studentBaseDao.delete(student));

    }


}
