package com.xy.amsd.admin.util;

import java.util.UUID;

/**
 * 密码工具类
 */
public class PasswordUtils {

    /**
     * 判断明文密码与密文是否一致
     * @param salt salt
     * @param encodedPassword 密文
     * @param rawPassword 明文
     * @return （ ）
     */
    public static boolean matches(String salt, String encodedPassword, String rawPassword) {
        return new PasswordEncoder(salt).matches(encodedPassword, rawPassword);
    }

    /**
     * 将明文密码加密
     * @param rawPassword 明文密码
     * @param salt salt
     * @return
     */
    public static String encode(String rawPassword, String salt) {
        return new PasswordEncoder(salt).encode(rawPassword);
    }

    /**
     * 使用UUID生成salt
     * @return salt
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }
}
