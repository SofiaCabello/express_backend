package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.express_backend.dto.*;
import org.example.express_backend.service.CustomerService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@Api(tags = "用户管理接口") // 添加了@Api注解，定义了该Controller的描述信息
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 登录
     * @param customerLoginDTO 邮箱和密码
     * @return JSON Web Token
     */
    @ApiOperation("用户登录") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/login")
    public Result login(@RequestBody CustomerLoginDTO customerLoginDTO){
        try{
            VerifyDTO result = customerService.login(customerLoginDTO);
            if(result == null){
                return Result.error("登录失败");
            }
            return Result.ok(result).message("登录成功");
        }
        catch (Exception e){
            return Result.error("登录失败");
        }
    }

    /**
     * 注册
     * @param customerRegisterDTO 邮箱、密码、验证码
     * @return JSON Web Token
     */
    @ApiOperation("用户注册") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/register")
    public Result register(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        try{
            return Result.ok(customerService.register(customerRegisterDTO)).message("注册成功");
        }
        catch (Exception e){
            return Result.error("注册失败");
        }
    }

    /**
     * 发送邮箱验证码
     * @param emailDTO 邮箱
     * @return 是否发送成功
     */
    @ApiOperation("发送邮箱验证码") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/sendEmailVerifyCode")
    public Result sendEmailVerifyCode(@RequestBody EmailDTO emailDTO){
        if(customerService.sendEmailVerifyCode(emailDTO.getEmail())){
            return Result.ok().message("发送成功");
        }
        return Result.error("发送失败");
    }

    /**
     * 添加地址
     * @param addAddressDTO 地址
     * @return 是否添加成功
     */
    @ApiOperation("添加地址") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody AddAddressDTO addAddressDTO){
        if(customerService.addAddress(addAddressDTO)){
            return Result.ok().message("添加成功");
        }
        return Result.error("添加失败");
    }

    /**
     * 删除地址
     * @param addAddressDTO 地址
     * @return 是否删除成功
     */
    @ApiOperation("删除地址") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/deleteAddress")
    public Result deleteAddress(@RequestBody AddAddressDTO addAddressDTO){
        if(customerService.deleteAddress(addAddressDTO)){
            return Result.ok().message("删除成功");
        }
        return Result.error("删除失败");
    }

    @GetMapping("/getAddress")
    public Result getAddress(@RequestParam(required = true) Long id){
        try {
            return Result.ok(customerService.getAddress(id)).message("获取成功");
        } catch (Exception e) {
            return Result.error("获取失败");
        }
    }

    /**
     * 获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    @GetMapping("/getCustomerByEmail")
    public Result getCustomerByEmail(@RequestParam(required = true) String email){
        try {
            return Result.ok(customerService.getCustomerByEmail(email)).message("获取成功");
        } catch (Exception e) {
            return Result.error("获取失败");
        }
    }

    /**
     * 修改用户信息
     * @param customerInfoDTO 用户信息
     * @return 是否修改成功
     */
    @PostMapping("/updateCustomer")
    public Result updateCustomer(@RequestBody CustomerInfoDTO customerInfoDTO){
        if(customerService.updateCustomerInfo(customerInfoDTO)){
            return Result.ok().message("修改成功");
        }
        return Result.error("修改失败");
    }
}
