package org.example.express_backend.controller;

import org.example.express_backend.entity.Customer;
import org.example.express_backend.mapper.BatchMapper;
import org.example.express_backend.mapper.CustomerMapper;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private CustomerMapper customerMapper;
    @GetMapping("/hello")
    public Result hello() {

        return Result.ok("Hello World!").message("获取成功");
    }
}