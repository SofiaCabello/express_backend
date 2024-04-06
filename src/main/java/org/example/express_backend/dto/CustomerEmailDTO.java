package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerEmailDTO implements Serializable {
    private String email;
    private String code;
}