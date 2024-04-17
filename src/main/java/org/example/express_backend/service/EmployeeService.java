package org.example.express_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.constant.MessageConstant;
import org.example.express_backend.dto.EmployeeDTO;
import org.example.express_backend.dto.EmployeeLoginDTO;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.exception.PasswordErrorException;
import org.example.express_backend.mapper.EmployeeMapper;
import org.example.express_backend.util.PasswordUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 员工服务类，登录，揽收、运输、派送等业务过程
 */
@Service
public class EmployeeService extends ServiceImpl<EmployeeMapper, Employee> implements IService<Employee> {

    @Autowired
    private EmployeeMapper employeeMapper;


    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {//前端返回数据,email和password
        String email = employeeLoginDTO.getEmail();
        String password = employeeLoginDTO.getPassword();

        //1、根据邮箱名查询数据库中的数据
        Employee employee = employeeMapper.getEmployeeByEmail(email);


        //2、处理各种异常情况（邮箱名不存在、密码不对）
        if (employee == null) {
            //账号不存在
            throw new org.example.express_backend.exception.AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传来的明文密码进行md5加密处理，进而进行比对
        //password = DigestUtils.md5DigestAsHex(password.getBytes());


        //此处使用Util工具类进行密文密码比对
        password = PasswordUtil.encodePassword(password);
        if (PasswordUtil.checkPassword(password, employee.getPasswordHash())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }


        //3、返回实体对象
        return employee;
    }


    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    public void save(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();

        //对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        String password = employeeDTO.getPassword();
/*        //设置密码，默认密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));*/

        employee.setPasswordHash(PasswordUtil.encodePassword(password));

        employeeMapper.insert(employee);
    }

    /**
     * 修改员工
     *
     * @param employeeDTO
     */
    public void update(EmployeeDTO employeeDTO) {
        this.update(employeeDTO);
    }


    public Employee getEmployeeByEmail(String email) {
        return employeeMapper.getEmployeeByEmail(email);
    }
}
