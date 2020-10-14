package com.tjrac.studentAffairs.doman.user;

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
}
