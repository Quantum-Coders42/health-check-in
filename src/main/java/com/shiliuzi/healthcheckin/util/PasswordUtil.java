package com.shiliuzi.healthcheckin.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 密码工具类，提供密码加密和验证功能
 */
public class PasswordUtil {
    
    /**
     * 使用MD5加密密码
     * @param password 原始密码
     * @return 加密后的密码字符串
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
    
    /**
     * 验证密码是否正确
     * @param inputPassword 输入的密码
     * @param encryptedPassword 数据库中存储的加密密码
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String inputPassword, String encryptedPassword) {
        String encryptedInput = encryptPassword(inputPassword);
        return encryptedInput.equals(encryptedPassword);
    }
}