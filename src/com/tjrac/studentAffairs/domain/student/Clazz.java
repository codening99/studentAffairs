package com.tjrac.studentAffairs.domain.student;

/**
 * clazz 班级表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Clazz {
    private Integer clazz_id;           // 主键 自增 索引
    private String clazz_name;          // 班级名 唯一
    private Integer specialty_id;       // 专业id
    private Integer grade_id;           // 年级id

    public Clazz() {
    }

    public Clazz(Integer clazz_id) {
        this.clazz_id = clazz_id;
    }

    public Clazz(String clazz_name, Integer specialty_id, Integer grade_id) {
        this.clazz_name = clazz_name;
        this.specialty_id = specialty_id;
        this.grade_id = grade_id;
    }

    public Clazz(Integer clazz_id, String clazz_name, Integer specialty_id, Integer grade_id) {
        this.clazz_id = clazz_id;
        this.clazz_name = clazz_name;
        this.specialty_id = specialty_id;
        this.grade_id = grade_id;
    }

    public Integer getClazz_id() {
        return clazz_id;
    }

    public void setClazz_id(Integer clazz_id) {
        this.clazz_id = clazz_id;
    }

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }

    public Integer getSpecialty_id() {
        return specialty_id;
    }

    public void setSpecialty_id(Integer specialty_id) {
        this.specialty_id = specialty_id;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "clazz_id=" + clazz_id +
                ", clazz_name='" + clazz_name + '\'' +
                ", specialty_id=" + specialty_id +
                ", grade_id=" + grade_id +
                '}';
    }
}
