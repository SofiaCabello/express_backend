package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.entity.Employee;

/**
 * 员工表 Mapper
 * employee(id, name, phone, password_hash, salt, serve_at)
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 新建员工
     * @param employee 员工实体
     */
    @Insert("INSERT INTO employee(name, phone, password_hash, salt, serve_at) VALUES(#{name}, #{phone}, #{passwordHash}, #{salt}, #{serveAt})")
    void createEmployee(Employee employee);

    /**
     * 根据电话号码查找员工
     * @param phone 电话号码
     * @return Employee 员工实体
     */
    @Select("SELECT * FROM employee WHERE phone = #{phone}")
    Employee getEmployeeByPhone(String phone);

    /**
     * 根据真实姓名查找员工
     * @param name 真实姓名
     * @return Employee 员工实体
     */
    @Select("SELECT * FROM employee WHERE name = #{name}")
    Employee getEmployeeByName(String name);
}
