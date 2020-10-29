package com.tjrac.studentAffairs.domain.user;

/**
 * student 学生表
 *
 * @author : xziying
 * @create : 2020-10-14 21:28
 */
public class Student implements User{
    private Integer student_id;        // 学生id 自增 索引
    private String sno;                // 学号 唯一 不能为空
    private String name;               // 学生姓名
    private String password;           // 密码
    private String student_sex;        // 学生性别
    private Integer competence;        // 权限 参考权限表
    private Integer grade_id;         // 年级，一般为年份，方便管理
    private Integer department_id;    // 系
    private Integer specialty_id;     // 专业
    private Integer direction_id;     // 方向
    private Integer clazz_id;         // 班级

    public Student() {
    }

    public Student(Integer student_id) {
        this.student_id = student_id;
    }

    public Student(String sno, String name, String password, String student_sex, Integer competence, Integer grade_id, Integer department_id, Integer specialty_id, Integer direction_id, Integer clazz_id) {
        this.sno = sno;
        this.name = name;
        this.password = password;
        this.student_sex = student_sex;
        this.competence = competence;
        this.grade_id = grade_id;
        this.department_id = department_id;
        this.specialty_id = specialty_id;
        this.direction_id = direction_id;
        this.clazz_id = clazz_id;
    }

    public Student(Integer student_id, String sno, String name, String password, String student_sex, Integer competence, Integer grade_id, Integer department_id, Integer specialty_id, Integer direction_id, Integer clazz_id) {
        this.student_id = student_id;
        this.sno = sno;
        this.name = name;
        this.password = password;
        this.student_sex = student_sex;
        this.competence = competence;
        this.grade_id = grade_id;
        this.department_id = department_id;
        this.specialty_id = specialty_id;
        this.direction_id = direction_id;
        this.clazz_id = clazz_id;
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

    public Integer getCompetence() {
        return competence;
    }

    public void setCompetence(Integer competence) {
        this.competence = competence;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
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

    public Integer getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(Integer direction_id) {
        this.direction_id = direction_id;
    }

    public Integer getClazz_id() {
        return clazz_id;
    }

    public void setClazz_id(Integer clazz_id) {
        this.clazz_id = clazz_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_id=" + student_id +
                ", sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", student_sex='" + student_sex + '\'' +
                ", competence=" + competence +
                ", grade_id=" + grade_id +
                ", department_id=" + department_id +
                ", specialty_id=" + specialty_id +
                ", direction_id=" + direction_id +
                ", clazz_id=" + clazz_id +
                '}';
    }
}
