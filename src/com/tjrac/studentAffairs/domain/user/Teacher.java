package com.tjrac.studentAffairs.domain.user;

/**
 * teacher 教师表
 *
 * @author : xziying
 * @create : 2020-10-14 21:28
 */
public class Teacher {
    Integer teacher_id;     // 教师ID 自增 索引
    String account;         // 账号
    String password;        // 密码
    String name;            // 姓名
    Integer competence;     // 权限 参考权限表

    public Teacher() {
    }

    public Teacher(Integer teacher_id, String account, String password, String name, Integer competence) {
        this.teacher_id = teacher_id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.competence = competence;
    }

    public Teacher(String account, String password, String name, Integer competence) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.competence = competence;
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Integer teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCompetence() {
        return competence;
    }

    public void setCompetence(Integer competence) {
        this.competence = competence;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacher_id=" + teacher_id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", competence=" + competence +
                '}';
    }
}
