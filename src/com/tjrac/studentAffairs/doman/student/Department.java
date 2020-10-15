package com.tjrac.studentAffairs.doman.student;

/**
 * department 系表
 *
 * @author : xziying
 * @create : 2020-10-14 22:32
 */
public class Department {
    private Integer department_id;      // 主键 自增 索引
    private String name;                // 系名 唯一

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(Integer department_id, String name) {
        this.department_id = department_id;
        this.name = name;
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
        return "Department{" +
                "department_id=" + department_id +
                ", name='" + name + '\'' +
                '}';
    }
}
