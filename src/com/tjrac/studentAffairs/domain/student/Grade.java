package com.tjrac.studentAffairs.domain.student;

/**
 * Grade 年级
 *
 * @author ZeNing
 * @create 2020-10-15 9:31
 */
public class Grade {
    private Integer grade_id;  //主键 年级id
    private String grade_name;      //年级名 唯一

    public Grade() {
    }

    public Grade(String grade_name) {
        this.grade_name = grade_name;
    }

    public Grade(Integer grade_id, String grade_name) {
        this.grade_id = grade_id;
        this.grade_name = grade_name;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "grade_id=" + grade_id +
                ", grade_name='" + grade_name + '\'' +
                '}';
    }
}
