package org.example.express_backend.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Employees {
    private Integer id;
    private String name;
    private String phone;
    private String passwordHash;
    private String salt;
    private Integer serveAt;
}
