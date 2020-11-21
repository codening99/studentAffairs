package com.tjrac.studentAffairs.domain.student;

/**
 * Direction 方向
 *
 * @author ZeNing
 * @create 2020-10-15 9:36
 */
public class Direction {
    private Integer direction_id;  //方向id 主键
    private String direction_name; //方向名 唯一
    private Integer grade_id; //系id
    private Integer specialty_id;  //专业id

    public Direction() {
    }

    public Direction(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public Direction(String direction_name, Integer grade_id, Integer specialty_id) {
        this.direction_name = direction_name;
        this.grade_id = grade_id;
        this.specialty_id = specialty_id;
    }

    public Direction(Integer direction_id, String direction_name, Integer grade_id, Integer specialty_id) {
        this.direction_id = direction_id;
        this.direction_name = direction_name;
        this.grade_id = grade_id;
        this.specialty_id = specialty_id;
    }

    public Integer getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public String getDirection_name() {
        return direction_name;
    }

    public void setDirection_name(String direction_name) {
        this.direction_name = direction_name;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
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
                ", direction_name='" + direction_name + '\'' +
                ", grade_id=" + grade_id +
                ", specialty_id=" + specialty_id +
                '}';
    }
}
