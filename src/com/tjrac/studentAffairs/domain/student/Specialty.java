package com.tjrac.studentAffairs.domain.student;

/**
 * specialty 专业表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Specialty {
    private Integer specialty_id;       // 主键 自增 索引
    private Integer department_id;      // 对应系id
    private String specialty_name;      // 专业名

    public Specialty() {
    }

    public Specialty(Integer department_id, String specialty_name) {
        this.department_id = department_id;
        this.specialty_name = specialty_name;
    }

    public Specialty(Integer specialty_id, Integer department_id, String specialty_name) {
        this.specialty_id = specialty_id;
        this.department_id = department_id;
        this.specialty_name = specialty_name;
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

    public String getSpecialty_name() {
        return specialty_name;
    }

    public void setSpecialty_name(String specialty_name) {
        this.specialty_name = specialty_name;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "specialty_id=" + specialty_id +
                ", department_id=" + department_id +
                ", specialty_name='" + specialty_name + '\'' +
                '}';
    }
}
