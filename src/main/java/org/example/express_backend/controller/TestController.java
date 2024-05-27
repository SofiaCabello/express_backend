package org.example.express_backend.controller;

import org.example.express_backend.mapper.CustomerMapper;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*")
public class TestController {
    @Autowired
    private CustomerMapper customerMapper;
    @GetMapping("/hello")
    public Result hello() {

        return Result.ok("Hello World!").message("获取成功");
    }

    @PostMapping("/login")
    public Result login(@RequestBody String data) {
        // return a mocked tokenMap
        Map<String, Object> tokenMap = Map.of("token", "mockedToken");
        return Result.ok(tokenMap);
    }

    @GetMapping("/info")
    public Result getInfo(@RequestParam String token){
        // return a mocked userInfo
        Map<String, Object> userInfo = Map.of("name", "mockedName", "role", "admin");
        return Result.ok(userInfo);
    }
}