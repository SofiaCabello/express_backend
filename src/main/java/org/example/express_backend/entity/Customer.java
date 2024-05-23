package org.example.express_backend.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "customer", autoResultMap = true)
public class Customer {
    private Long id;
    private String username;
    private String phone;
    private String email;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private JSONArray address; // MySQL: JSON
    private String passwordHash;
}
