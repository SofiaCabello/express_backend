package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.express_backend.dto.EmployeeDTO;
import org.example.express_backend.dto.EmployeeLoginDTO;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.service.EmployeeService;
import org.example.express_backend.util.JwtUtil;
import org.example.express_backend.util.Result;
import org.example.express_backend.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Slf4j
@Api("员工端接口开发")
@CrossOrigin(origins = "*")
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

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.ok();
    }


    /**
     * 新增员工,注册员工
     * @param employeeDTO
     * @return
     */
    @PostMapping("/enroll")
    @ApiOperation("新增注册员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
//        Employee employee = new Employee();
//        BeanUtil.copyProperties(employeeDTO, employee);
        employeeService.save(employeeDTO);
        return Result.ok();
    }


    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return Result.ok(employee);
    }

    @PostMapping("/update")
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){
        log.info("修改员工信息：{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.ok();
    }


    /**
     * 查询所有员工信息
     * @return
     */
    @GetMapping("/findAllEmployees")
    @ApiOperation("查询所有员工信息")
    public Result<List<Employee>> getAll(){
        return Result.ok(employeeService.list());
    }

    /**
     * 根据邮箱查询员工信息
     * @param email
     * @return
     */
    @GetMapping("/findEmployeeByEmail/{email}")
    @ApiOperation("根据邮箱查询员工信息")
    public Result<Employee> getEmployeeByEmail(@PathVariable String email){
        return Result.ok(employeeService.getEmployeeByEmail(email));
    }



}
