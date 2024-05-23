package org.example.express_backend.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.example.express_backend.dto.CustomerInfoDTO;

import org.example.express_backend.entity.BackPage;
import org.example.express_backend.entity.Customer;

import org.example.express_backend.service.admin.AdminCustomerService;

import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname AdminCustomerController
 * @Description
 * @Date 2024/5/11 12:01
 * @Created by Zhiend
 */
@RestController
@RequestMapping("/adminCus")
@Slf4j
@Api("用户管理端接口开发")
@CrossOrigin(origins = "*")
public class AdminCustomerController {
    @Autowired
    private AdminCustomerService adminCustomerService;

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    @GetMapping("/getAllPages")
    public BackPage<Customer> queryCustomerPage(@RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {
        return adminCustomerService.queryCustomersPage(pageNo, pageSize);
    }

    /**
     * 根据用户id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/getCustomerById")
    public Result<Customer> getCustomerById(@RequestParam(required = true) Long id){
        //使用mybatisPlus的查询方法
        return Result.ok(adminCustomerService.getById(id));
    }

    /**
     * 修改用户信息
     * @param customerInfoDTO 用户信息
     * @return 是否修改成功
     */
    @PostMapping("/update")
    public Result updateCustomer(@RequestBody CustomerInfoDTO customerInfoDTO){
        Customer customer = new Customer();
        //拷贝属性
        BeanUtil.copyProperties(customerInfoDTO, customer);
        //使用mybatisPlus的修改方法，封装了updateById方法
        adminCustomerService.updateById(customer);
        return Result.ok();
    }

    //删除用户信息
    @PostMapping("/delete")
    public Result deleteCustomer(@RequestParam("id") Long id) {
        adminCustomerService.removeById(id);
        return Result.ok();
    }

}
