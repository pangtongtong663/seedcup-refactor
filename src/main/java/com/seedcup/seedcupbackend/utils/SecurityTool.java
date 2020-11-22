package com.seedcup.seedcupbackend.utils;

import org.apache.commons.codec.binary.Hex;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityTool {
    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String data, String salt) {
        /*
         * @Author holdice
         * @Description 通过MD5加盐的方式对密码进行加密
         * @Date 2020/11/22 4:26 下午
         * @Param [password, salt]
         * @return java.lang.String
         */
        String passwordAndSalt = data + salt;
        return Hex.encodeHexString(md5.digest(passwordAndSalt.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean match(String encrypted, String notEncrypted, String salt) {
        return encrypted.equals(encrypt(notEncrypted, salt));
    }
}
