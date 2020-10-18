package com.tjrac.studentAffairs.domain.user;

/**
 * student 学生表
 *
 * @author : xziying
 * @create : 2020-10-14 21:28
 */
public class Student {
    private Integer student_id;        // 学生id 自增 索引
    private String sno;                // 学号 唯一 不能为空
    private String name;               // 学生姓名
    private String password;           // 密码
    private String student_sex;        // 学生性别
    private Integer competence_id;        // 权限 参考权限表
    private String grade_name;         // 年级，一般为年份，方便管理
    private String department_name;    // 系
    private String specialty_name;     // 专业
    private String direction_name;     // 方向
    private String clazz_name;         // 班级

    public Student() {

    }

    public Student(String sno, String name, String password, String student_sex, Integer competence_id, String grade_name, String department_name, String specialty_name, String direction_name, String clazz_name) {
        this.sno = sno;
        this.name = name;
        this.password = password;
        this.student_sex = student_sex;
        this.competence_id = competence_id;
        this.grade_name = grade_name;
        this.department_name = department_name;
        this.specialty_name = specialty_name;
        this.direction_name = direction_name;
        this.clazz_name = clazz_name;
    }

    public Student(Integer student_id, String sno, String name, String password, String student_sex, Integer competence_id, String grade_name, String department_name, String specialty_name, String direction_name, String clazz_name) {
        this.student_id = student_id;
        this.sno = sno;
        this.name = name;
        this.password = password;
        this.student_sex = student_sex;
        this.competence_id = competence_id;
        this.grade_name = grade_name;
        this.department_name = department_name;
        this.specialty_name = specialty_name;
        this.direction_name = direction_name;
        this.clazz_name = clazz_name;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudent_sex() {
        return student_sex;
    }

    public void setStudent_sex(String student_sex) {
        this.student_sex = student_sex;
    }

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer competence_id) {
        this.competence_id = competence_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getSpecialty_name() {
        return specialty_name;
    }

    public void setSpecialty_name(String specialty_name) {
        this.specialty_name = specialty_name;
    }

    public String getDirection_name() {
        return direction_name;
    }

    public void setDirection_name(String direction_name) {
        this.direction_name = direction_name;
    }

    public String getClazz_name() {
        return clazz_name;
    }

    public void setClazz_name(String clazz_name) {
        this.clazz_name = clazz_name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", student_sex='" + student_sex + '\'' +
                ", competence_id=" + competence_id +
                ", grade_name='" + grade_name + '\'' +
                ", department_name='" + department_name + '\'' +
                ", specialty_name='" + specialty_name + '\'' +
                ", direction_name='" + direction_name + '\'' +
                ", clazz_name='" + clazz_name + '\'' +
                '}';
    }
}
