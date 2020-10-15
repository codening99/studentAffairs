package com.tjrac.studentAffairs.doman.user;

/**
 * student 学生表
 *
 * @author : xziying
 * @create : 2020-10-14 21:28
 */
public class Student {
    private Integer student_id;     // 学生id 自增 索引
    private String sno;             // 学号 唯一 不能为空
    private String password;        // 密码
    private Integer competence;     // 权限 参考权限表
    private Integer grade;          // 年级，一般为年份，方便管理
    private String clazz;           // 班级
    private String department;      // 系
    private String specialty;       // 专业
    private String direction;       // 方向

    public Student() {
    }

    public Student(String sno, String password, Integer competence, Integer grade, String clazz, String department, String specialty, String direction) {
        this.sno = sno;
        this.password = password;
        this.competence = competence;
        this.grade = grade;
        this.clazz = clazz;
        this.department = department;
        this.specialty = specialty;
        this.direction = direction;
    }

    public Student(Integer student_id, String sno, String password, Integer competence, Integer grade, String clazz, String department, String specialty, String direction) {
        this.student_id = student_id;
        this.sno = sno;
        this.password = password;
        this.competence = competence;
        this.grade = grade;
        this.clazz = clazz;
        this.department = department;
        this.specialty = specialty;
        this.direction = direction;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCompetence() {
        return competence;
    }

    public void setCompetence(Integer competence) {
        this.competence = competence;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", sno='" + sno + '\'' +
                ", password='" + password + '\'' +
                ", competence=" + competence +
                ", grade=" + grade +
                ", clazz='" + clazz + '\'' +
                ", department='" + department + '\'' +
                ", specialty='" + specialty + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
