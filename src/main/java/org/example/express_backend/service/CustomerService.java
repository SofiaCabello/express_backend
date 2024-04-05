package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.express_backend.dto.AddAddressDTO;
import org.example.express_backend.dto.CustomerEmailDTO;
import org.example.express_backend.dto.CustomerVerifyDTO;
import org.example.express_backend.entity.Customer;
import org.example.express_backend.mapper.CustomerMapper;
import org.example.express_backend.util.EmailUtil;
import org.example.express_backend.util.JwtUtil;
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
     * @param customerEmailDTO 邮箱和验证码
     * @return 是否正确
     */
    public boolean verifyEmail(CustomerEmailDTO customerEmailDTO){
        return emailUtil.isCorrect(customerEmailDTO.getEmail(), customerEmailDTO.getCode());
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
     * @param customerVerifyDTO 注册信息
     * @return JSON Web Token
     */
    public String register(CustomerVerifyDTO customerVerifyDTO) {
        // 检查邮箱是否已经注册
        if(isRegistered(customerVerifyDTO.getEmail())){
            return null;
        }
        // 注册
        Customer customer = new Customer();
        customer.setEmail(customerVerifyDTO.getEmail());
        customer.setPasswordHash(PasswordUtil.encodePassword(customerVerifyDTO.getPassword()));
        customerMapper.insert(customer);
        JwtUtil.generateToken(customer.getEmail());
        return JwtUtil.generateToken(customer.getEmail());
    }

    /**
     * 登录
     * @param customerVerifyDTO 登录信息
     * @return JSON Web Token
     */
    public String login(CustomerVerifyDTO customerVerifyDTO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", customerVerifyDTO.getEmail());
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return null;
        }
        if (PasswordUtil.checkPassword(customerVerifyDTO.getPassword(), customer.getPasswordHash())) {
            return JwtUtil.generateToken(customer.getEmail());
        }
        return null;
    }

    /**
     * 为地址簿添加地址
     * @param addAddressDTO 用户ID和地址
     * @return 是否添加成功
     */
    public boolean addAddress(AddAddressDTO addAddressDTO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addAddressDTO.getId())
                .select("address");
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return false;
        }
        customer.getAddress().add(addAddressDTO.getAddress());
        customerMapper.updateById(customer);
        return true;
    }

    /**
     * 为地址簿删除地址
     * @param addAddressDTO 用户ID和地址
     * @return 是否删除成功
     */
    public boolean deleteAddress(AddAddressDTO addAddressDTO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", addAddressDTO.getId())
                .select("address");
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return false;
        }
        customer.getAddress().remove(addAddressDTO.getAddress());
        customerMapper.updateById(customer);
        return true;
    }
}
