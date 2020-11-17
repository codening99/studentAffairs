package com.tjrac.studentAffairs.test;

import com.tjrac.studentAffairs.domain.user.Student;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

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
    public void test() {
        System.out.println(4%2);
    }

}
