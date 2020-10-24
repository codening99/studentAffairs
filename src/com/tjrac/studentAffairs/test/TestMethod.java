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


    @Test
    public void test4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Student student = new Student();

        Method setSno = Student.class.getMethod("setSno", String.class);

        setSno.invoke(student, "6018203020");

        System.out.println(student);


    }

    @Test
    public void test5() {

        Student student = new Student("6018203020","张飞", "123456" , "男",4,"计算机","计算机","计算机","计算机","计算机");

        System.out.println(student);
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);

        System.out.println(studentBaseDao.insert(student));

    }

}
