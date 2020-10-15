package com.tjrac.studentAffairs.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author ZeNing
 * @create 2020-10-07 22:17
 */
public class MD5 {

    public static String MD5Encrypt(String oldPassword) {
        StringBuilder sb = new StringBuilder(32);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(oldPassword.getBytes(StandardCharsets.UTF_8));

            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).toUpperCase(), 1, 3);
            }
        } catch (Exception e) {
            System.out.println("Can not encode the string '" + oldPassword + "' to MD5!");
            return null;
        }

        return sb.toString();
    }

}
