package com.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

import cn.hutool.core.util.RandomUtil;

/**
 * 密码哈希工具：SHA-256 + 随机盐。
 * 存储格式：{salt}:{hash}
 * hash = sha256(salt + password)
 */
public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成随机密码（用于管理员重置）
     */
    public static String randomPassword() {
        return RandomUtil.randomString(12);
    }

    /**
     * 生成 {salt}:{hash}
     */
    public static String hash(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        String salt = RandomUtil.randomString(16);
        return salt + ":" + sha256(salt + rawPassword);
    }

    /**
     * 校验密码。支持三种情况：
     * 1. 存储值为 {salt}:{hash} 格式 → 标准校验
     * 2. 存储值为旧版明文且与输入一致 → 返回 true（调用方应随后升级存储为哈希）
     * 3. 其他 → 校验失败
     */
    public static boolean matches(String rawPassword, String stored) {
        if (rawPassword == null || stored == null) {
            return false;
        }
        int idx = stored.indexOf(':');
        if (idx > 0 && idx == stored.length() - 33) { // salt 长度 16，hash 长度 32
            String salt = stored.substring(0, idx);
            String hash = stored.substring(idx + 1);
            return sha256(salt + rawPassword).equalsIgnoreCase(hash);
        }
        // 兼容旧明文数据
        return stored.equals(rawPassword);
    }

    /**
     * 存储值是否为哈希格式
     */
    public static boolean isHashed(String stored) {
        return stored != null && stored.indexOf(':') == 16 && stored.length() == 49;
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("sha256 error", e);
        }
    }
}
