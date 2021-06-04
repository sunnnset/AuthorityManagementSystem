package com.xy.ams.admin.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };
    private final static String MD5 = "MD5";
    private final static String SHA = "SHA";

    private Object salt;
    private String algorithm;

    public PasswordEncoder(Object salt) {
        this(salt, MD5);
    }

    public PasswordEncoder(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     * 将明文密码加密（加盐，哈希）
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encode(String rawPassword) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            String passwordWithSalt = mergePasswordAndSalt(rawPassword);
            result = byteArrayToHexString(messageDigest.digest(passwordWithSalt.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 比较明文密码与加密后的密码是否一致
     * @param encodedPassword 加密后的密码
     * @param rawPassword 明文密码
     * @return （ ）
     */
    public boolean matches(String encodedPassword, String rawPassword) {
        String pass1 = "" + encodedPassword;
        String pass2 = encode(rawPassword);
        return  pass1.equals(pass2);
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) password = "";
        if (salt == null || "".equals(salt)) {
            return password;
        } else {
            return password + "{" +salt.toString() + "}";
        }
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(byteToHexString(b));
        }
        return result.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
