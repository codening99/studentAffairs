package com.tjrac.studentAffairs.doman.student;

/**
 * clazz 班级表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Clazz {
    private Integer clazz_id;           // 主键 自增 索引
    private String name;                // 班级名 唯一
    private Integer specialty_id;       // 专业id
    private Integer grade;              // 年级，一般为年份，方便管理

    public Clazz() {
    }

    public Clazz(Integer clazz_id, String name, Integer specialty_id, Integer grade) {
        this.clazz_id = clazz_id;
        this.name = name;
        this.specialty_id = specialty_id;
        this.grade = grade;
    }

    public Clazz(String name, Integer specialty_id, Integer grade) {
        this.name = name;
        this.specialty_id = specialty_id;
        this.grade = grade;
    }

    public Integer getClazz_id() {
        return clazz_id;
    }

    public void setClazz_id(Integer clazz_id) {
        this.clazz_id = clazz_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSpecialty_id() {
        return specialty_id;
    }

    public void setSpecialty_id(Integer specialty_id) {
        this.specialty_id = specialty_id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "clazz_id=" + clazz_id +
                ", name='" + name + '\'' +
                ", specialty_id=" + specialty_id +
                ", grade=" + grade +
                '}';
    }
}
