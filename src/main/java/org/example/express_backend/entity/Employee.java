package org.example.express_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Employee {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String passwordHash;
    private Long serveAt;
}
