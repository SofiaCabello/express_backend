package org.example.express_backend.controller.admin;


import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.express_backend.dto.EmployeeDTO;
import org.example.express_backend.entity.BackPage;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.service.admin.AdminEmployeeService;
import org.example.express_backend.util.PasswordUtil;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname AdminEmployeeController
 * @Description 员工管理端接口开发
 * @Date 2024/5/11 8:11
 * @Created by Zhiend
 */
@RestController
@RequestMapping("/adminEmp")
@Slf4j
@Api("员工管理端接口开发")
@CrossOrigin(origins = "*")
public class AdminEmployeeController {
    @Autowired
    AdminEmployeeService adminEmployeeService;

    //获取所有员工分页信息
    @GetMapping("/getAllPages")
    public BackPage<Employee> queryEmployeesPage(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {
        return adminEmployeeService.queryEmployeesPage(pageNo, pageSize);
    }

    /**
     * 根据用户id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/getEmployeeById")
    public Result<Employee> getEmployeeById(@RequestParam("id") Long id) {
        return Result.ok(adminEmployeeService.getById(id));
    }

    //修改员工信息,返回信息用Result封装，使用mybatisPlus的updateById方法
    @PostMapping("/update")
    public Result updateEmployee(@RequestBody Employee employee) {
        //密码加密
        employee.setPasswordHash(PasswordUtil.encodePassword(employee.getPasswordHash()));
        adminEmployeeService.updateById(employee);
        return Result.ok();
    }

    //删除员工信息
    @PostMapping("/delete")
    public Result deleteEmployee(@RequestParam("id") Long id) {
        adminEmployeeService.removeById(id);
        return Result.ok();
    }

    //添加员工信息
    @PostMapping("/create")
    public Result createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        //拷贝属性
        BeanUtil.copyProperties(employeeDTO, employee);
        //密码加密
        employee.setPasswordHash(PasswordUtil.encodePassword(employeeDTO.getPassword()));
        adminEmployeeService.save(employee);
        return Result.ok();
    }


}
