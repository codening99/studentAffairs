package com.tjrac.studentAffairs.domain.student;

/**
 * department 系表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Department {
    private Integer department_id;      // 主键 自增 索引
    private String department_name;                // 系名 唯一

    public Department() {
    }

    public Department(String department_name) {
        this.department_name = department_name;
    }

    public Department(Integer department_id, String department_name) {
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "department_id=" + department_id +
                ", department_name='" + department_name + '\'' +
                '}';
    }
}
