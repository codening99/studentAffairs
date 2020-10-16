package com.tjrac.studentAffairs.test;

import com.tjrac.studentAffairs.domain.user.Teacher;
import org.junit.Test;

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

    }

}
