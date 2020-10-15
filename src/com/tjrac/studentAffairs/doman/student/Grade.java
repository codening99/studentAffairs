package com.tjrac.studentAffairs.doman.student;

/**
 * Grade 年级
 *
 * @author ZeNing
 * @create 2020-10-15 9:31
 */
public class Grade {
    private Integer grade_id;  //主键 年级id
    private String name;      //年级名 唯一

    public Grade() {
    }

    public Grade(String name) {
        this.name = name;
    }

    public Grade(Integer grade_id, String name) {
        this.grade_id = grade_id;
        this.name = name;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "grade_id=" + grade_id +
                ", name='" + name + '\'' +
                '}';
    }
}
