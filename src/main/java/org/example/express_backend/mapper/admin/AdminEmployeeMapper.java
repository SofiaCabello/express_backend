package org.example.express_backend.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.express_backend.entity.Employee;
import org.mapstruct.Mapper;

/**
 * @Classname AdminEmployeeMapper
 * @Description 管理员员工管理
 * @Date 2024/5/11 8:15
 * @Created by Zhiend
 */
@Mapper
public interface AdminEmployeeMapper extends BaseMapper<Employee> {
}
