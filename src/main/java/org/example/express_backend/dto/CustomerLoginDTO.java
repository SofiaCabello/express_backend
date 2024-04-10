package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerLoginDTO implements Serializable {
    private String email;   // 邮箱
    private String password;   // 密码
}
