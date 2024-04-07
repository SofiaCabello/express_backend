package org.example.express_backend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmployeeDTO {
    private String name;
    private String phone;
    private String password;
    private String salt;
    private String serveAt;
}
