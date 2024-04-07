package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.express_backend.dto.AddAddressDTO;
import org.example.express_backend.dto.CustomerEmailDTO;
import org.example.express_backend.dto.CustomerVerifyDTO;
import org.example.express_backend.service.CustomerService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@Api(tags = "用户管理接口") // 添加了@Api注解，定义了该Controller的描述信息
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 登录
     * @param customerVerifyDTO 邮箱和密码
     * @return JSON Web Token
     */
    @ApiOperation("用户登录") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/login")
    public Result login(@RequestBody CustomerVerifyDTO customerVerifyDTO){
        String token = customerService.login(customerVerifyDTO);
        if(token != null){
            return Result.ok(token).message("登录成功");
        }
        return Result.error("登录失败");
    }

    /**
     * 注册
     * @param customerVerifyDTO 邮箱和密码
     * @return JSON Web Token
     */
    @ApiOperation("用户注册") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/register")
    public Result register(@RequestBody CustomerVerifyDTO customerVerifyDTO){
        String token = customerService.register(customerVerifyDTO);
        if(token != null){
            return Result.ok(token).message("注册成功");
        }
        return Result.error("注册失败");
    }

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return 是否发送成功
     */
    @ApiOperation("发送邮箱验证码") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/sendEmailVerifyCode")
    public Result sendEmailVerifyCode(@RequestBody String email){
        if(customerService.sendEmailVerifyCode(email)){
            return Result.ok().message("发送成功");
        }
        return Result.error("发送失败");
    }

    /**
     * 验证邮箱验证码是否正确
     * @param customerEmailDTO 邮箱和验证码
     * @return 是否正确
     */
    @ApiOperation("验证邮箱验证码是否正确") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/verifyEmail")
    public Result verifyEmail(@RequestBody CustomerEmailDTO customerEmailDTO){
        if(customerService.verifyEmail(customerEmailDTO)){
            return Result.ok().message("验证成功");
        }
        return Result.error("验证失败");
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
}
