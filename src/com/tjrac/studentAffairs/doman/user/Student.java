package com.tjrac.studentAffairs.doman.user;

/**
 * student 学生表
 *
 * @author : xziying
 * @create : 2020-10-14 21:28
 */
public class Student {
    Integer student_id;     // 学生id 自增 索引
    String sno;             // 学号 唯一 不能为空
    String password;        // 密码
    Integer competence;     // 权限 参考权限表
    Integer grade;          // 年级，一般为年份，方便管理
    String clazz;           // 班级
    String department;      // 系
    String specialty;       // 专业
    String direction;       // 方向


}
