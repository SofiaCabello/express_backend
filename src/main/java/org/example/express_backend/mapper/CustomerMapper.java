package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.entity.Customer;

/**
 * customer表的mapper接口
 * customer(id, username, phone, password_hash, salt, address)
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    /**
     * 根据用户名查找客户
     * @param username 用户名
     * @return Customer 客户实体
     */
    @Select("SELECT * FROM customer WHERE username = #{username}")
    Customer getCustomerByUsername(String username);

    /**
     * 根据电话号码查找客户
     * @param phone 电话号码
     * @return Customer 客户实体
     */
    @Select("SELECT * FROM customer WHERE phone = #{phone}")
    Customer getCustomerByPhone(String phone);
}
