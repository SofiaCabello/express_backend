package org.example.express_backend.service;

import org.example.express_backend.constant.MessageConstant;
import org.example.express_backend.dto.EmployeeLoginDTO;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.exception.PasswordErrorException;
import org.example.express_backend.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountNotFoundException;

/**
 * 员工服务类，登录，揽收、运输、派送等业务过程
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {//前端返回数据,name和password
        String username = employeeLoginDTO.getName();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getEmployeeByName(username);

        //2、处理各种异常情况（用户名不存在、密码不对）
        if (employee == null) {
            //账号不存在
            throw new org.example.express_backend.exception.AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传来的明文密码进行md5加密处理，进而进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPasswordHash())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }


        //3、返回实体对象
        return employee;
    }
}
