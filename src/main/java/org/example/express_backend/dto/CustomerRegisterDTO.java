package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRegisterDTO implements Serializable {
    private String email;   // 邮箱
    private String password;    // 密码
    private String code;    // 验证码
}
