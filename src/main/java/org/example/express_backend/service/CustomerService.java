package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.express_backend.entity.Customer;
import org.example.express_backend.mapper.CustomerMapper;
import org.example.express_backend.util.EmailUtil;
import org.example.express_backend.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类，用来处理和用户相关的业务逻辑
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    private EmailUtil emailUtil = new EmailUtil();
    private PasswordUtil passwordUtil = new PasswordUtil();

    /**
     * 发送邮箱验证码
     * @param email 邮箱
     * @return 是否发送成功
     */
    public boolean sendEmailVerifyCode(String email){
        return emailUtil.sendCode(email);
    }

    /**
     * 验证邮箱验证码是否正确
     * @param email 邮箱
     * @param code 验证码
     * @return 是否正确
     */
    public boolean verifyEmail(String email, String code) {
        return emailUtil.isCorrect(email, code);
    }

    /**
     * 是否已经注册
     * @param email 邮箱
     * @return 是否已经注册
     */
    public boolean isRegistered(String email) {
        // 检查邮箱是否已经注册
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return customerMapper.selectCount(queryWrapper) > 0;
    }

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @return 是否注册成功
     */
    public boolean register(String email, String password) {
        // 检查邮箱是否已经注册
        if (isRegistered(email)) {
            return false;
        }
        // 插入新用户
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPasswordHash(passwordUtil.encodePassword(password));
        return customerMapper.insert(customer) > 0; // .insert()返回插入的行数
    }
}
