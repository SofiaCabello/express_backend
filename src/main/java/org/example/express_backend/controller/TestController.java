package org.example.express_backend.controller;

import org.example.express_backend.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public Result hello() {
        return Result.ok("Hello World!").message("获取成功");
    }
}
