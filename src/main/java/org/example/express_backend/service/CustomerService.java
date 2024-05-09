package org.example.express_backend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.express_backend.dto.*;
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

    private final EmailUtil emailUtil;

    @Autowired
    public CustomerService(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    private Long generateUserId(){
        return System.currentTimeMillis();
    }

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
    private boolean verifyEmail(CustomerEmailDTO customerEmailDTO){
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
     * @param customerRegisterDTO 注册信息
     * @return JSON Web Token
     */
    public VerifyDTO register(CustomerRegisterDTO customerRegisterDTO) {
        // 检查邮箱是否已经注册
        if(isRegistered(customerRegisterDTO.getEmail())){
            return null;
        }
        // 验证邮箱验证码
        if(!verifyEmail(new CustomerEmailDTO(customerRegisterDTO.getEmail(), customerRegisterDTO.getCode()))){
            return null;
        }
        // 注册
        Customer customer = new Customer();
        customer.setId(generateUserId());
        customer.setEmail(customerRegisterDTO.getEmail());
        customer.setPasswordHash(PasswordUtil.encodePassword(customerRegisterDTO.getPassword()));
        customerMapper.insert(customer);
        JwtUtil.generateToken(customer.getEmail());
        return VerifyDTO.builder()
                .userId(customer.getId())
                .token(JwtUtil.generateToken(customer.getEmail()))
                .build();
    }

    /**
     * 登录
     * @param customerLoginDTO 登录信息
     * @return JSON Web Token
     */
    public VerifyDTO login(CustomerLoginDTO customerLoginDTO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", customerLoginDTO.getEmail());
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return null;
        }
        if (PasswordUtil.checkPassword(customerLoginDTO.getPassword(), customer.getPasswordHash())) {
            return VerifyDTO.builder()
                    .userId(customer.getId())
                    .token(JwtUtil.generateToken(customer.getEmail()))
                    .build();
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
        queryWrapper.eq("id", addAddressDTO.getId());
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return false;
        }

        JSONArray addressArray;

        if(customer.getAddress() != null && !customer.getAddress().isEmpty()) {
            addressArray = JSON.parseArray(customer.getAddress().toJSONString());
        } else {
            addressArray = new JSONArray();
        }

        addressArray.add(addAddressDTO.getAddress());
        String updateAddress = addressArray.toJSONString();
        customer.setAddress(JSON.parseArray(updateAddress));
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
        queryWrapper.eq("id", addAddressDTO.getId());
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return false;
        }

        JSONArray addressArray = JSON.parseArray(customer.getAddress().toJSONString());
        addressArray.remove(addAddressDTO.getAddress());
        String updateAddress = addressArray.toJSONString();
        customer.setAddress(JSON.parseArray(updateAddress));
        customerMapper.updateById(customer);
        return true;
    }

    /**
     * 获取地址簿
     * @param id 用户ID
     * @return 地址簿
     */
    public JSONArray getAddress(Long id) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id)
                .select("address");
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return null;
        }
        return customer.getAddress();
    }

    /**
     * 获取用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    public Customer getCustomerByEmail(String email) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return customerMapper.selectOne(queryWrapper);
    }

    /**
     * 修改用户信息，包括username phone email
     * @param customerInfoDTO 用户信息
     * @return 是否修改成功
     */
    public boolean updateCustomerInfo(CustomerInfoDTO customerInfoDTO) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", customerInfoDTO.getId());
        Customer customer = customerMapper.selectOne(queryWrapper);
        if (customer == null) {
            return false;
        }
        customer.setUsername(customerInfoDTO.getUsername());
        customer.setPhone(customerInfoDTO.getPhone());
        customer.setEmail(customerInfoDTO.getEmail());
        customerMapper.updateById(customer);
        return true;
    }
}
