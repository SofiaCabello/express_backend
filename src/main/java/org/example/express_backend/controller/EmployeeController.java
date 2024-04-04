package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.express_backend.dto.EmployeeLoginDTO;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.service.EmployeeService;
import org.example.express_backend.util.JwtUtil;
import org.example.express_backend.util.Result;
import org.example.express_backend.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
// TODO 需加入接口requestmapping
@RequestMapping
@Slf4j
@Api("员工端接口开发")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("登录接口设计")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        String token = JwtUtil.generateToken(employee.getEmail());

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .eamil(employee.getEmail())
                .token(token)
                .build();

        return Result.ok(employeeLoginVO);
    }
}
