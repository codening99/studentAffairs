package com.tjrac.studentAffairs.test;

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

}
