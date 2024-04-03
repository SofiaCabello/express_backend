package org.example.express_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address; // MySQL: JSON
    private String passwordHash;
    private String salt;
}
