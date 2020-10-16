package com.tjrac.studentAffairs.test;

import com.tjrac.studentAffairs.utils.MD5;
import org.junit.Test;

/**
 * 测试MD5加密系统
 *
 * @author ZeNing
 * @create 2020-10-15 20:36
 */
public class TestMD5 {

    @Test
    public void testOne() {
        //成功加密为32为字符
        System.out.println(MD5.MD5Encrypt("123456").length());
    }

}
