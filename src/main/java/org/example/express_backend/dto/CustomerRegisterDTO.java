package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerRegisterDTO implements Serializable {
    private String email;
    private String password;
    private String code;
}
