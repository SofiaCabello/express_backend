package org.example.express_backend.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private JSONArray address; // MySQL: JSON
    private String passwordHash;
}
