package org.example.express_backend.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.express_backend.entity.Customer;

/**
 * @Classname AdminCustomerMapper
 * @Description mapper
 * @Date 2024/5/11 12:05
 * @Created by Zhiend
 */
@Mapper
public interface AdminCustomerMapper extends BaseMapper<Customer> {
}
