package com.tjrac.studentAffairs.doman.student;

/**
 * Direction 方向
 *
 * @author ZeNing
 * @create 2020-10-15 9:36
 */
public class Direction {
    private Integer direction_id;  //方向id 主键
    private String name; //方向名 唯一
    private Integer department_id; //系id
    private Integer specialty_id; //专业id

    public Direction() {
    }

    public Direction(String name, Integer department_id, Integer specialty_id) {
        this.name = name;
        this.department_id = department_id;
        this.specialty_id = specialty_id;
    }

    public Direction(Integer direction_id, String name, Integer department_id, Integer specialty_id) {
        this.direction_id = direction_id;
        this.name = name;
        this.department_id = department_id;
        this.specialty_id = specialty_id;
    }

    public Integer getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public Integer getSpecialty_id() {
        return specialty_id;
    }

    public void setSpecialty_id(Integer specialty_id) {
        this.specialty_id = specialty_id;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "direction_id=" + direction_id +
                ", name='" + name + '\'' +
                ", department_id=" + department_id +
                ", specialty_id=" + specialty_id +
                '}';
    }
}
