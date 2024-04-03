package org.example.express_backend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 基于盐值的密码加密工具
 */
public class PasswordUtil {
    /**
     * 加密密码
     * @param password 密码
     * @return 加密后BCrypt密码
     */
    public String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * 检查密码是否正确
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否正确
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }
}
