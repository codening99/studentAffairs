package com.tjrac.studentAffairs.doman.student;

/**
 * specialty 专业表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Specialty {
    private Integer specialty_id;       // 主键 自增 索引
    private Integer department_id;      // 对应系id
    private String name;                // 专业名

    public Specialty(Integer specialty_id, Integer department_id, String name) {
        this.specialty_id = specialty_id;
        this.department_id = department_id;
        this.name = name;
    }

    public Specialty(Integer department_id, String name) {
        this.department_id = department_id;
        this.name = name;
    }

    public Specialty() {
    }

    public Integer getSpecialty_id() {
        return specialty_id;
    }

    public void setSpecialty_id(Integer specialty_id) {
        this.specialty_id = specialty_id;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialty_id=" + specialty_id +
                ", department_id=" + department_id +
                ", name='" + name + '\'' +
                '}';
    }
}
