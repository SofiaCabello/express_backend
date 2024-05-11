package org.example.express_backend.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.entity.BackPage;
import org.example.express_backend.entity.Employee;
import org.example.express_backend.mapper.admin.AdminEmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * @Classname AdminEmployeeService
 * @Description TODO
 * @Date 2024/5/11 8:15
 * @Created by Zhiend
 */
@Service
public class AdminEmployeeService extends ServiceImpl<AdminEmployeeMapper, Employee> implements IService<Employee> {


    //根据页码和每页分页数给出分页查询，使用wrapper
    public BackPage<Employee> queryEmployeesPage(Long pageNo, Long pageSize) {
        BackPage<Employee> EmployeeBackPage = new BackPage<>();
        // 设置条件构造器
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是第几页，而第二个参数是每页的记录数
        Page<Employee> EmployeePage = new Page<>(pageNo, pageSize);
        // page(EmployeePage, wrapper)这里的第一个参数就是上面定义了的Page对象，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<Employee> EmployeeIPage = page(EmployeePage, wrapper);
        // 封装数据，其中getRecords()是获取记录数，getCurrent()获取当前页数，getPages()获取总页数，getTotal()获取记录总数，还要其他更多的方法，大家可以自行查看，在这里就不过多赘述了
        EmployeeBackPage.setContentList(EmployeeIPage.getRecords());
        EmployeeBackPage.setCurrentPage(EmployeeIPage.getCurrent());
        EmployeeBackPage.setTotalPage(EmployeeIPage.getPages());
        EmployeeBackPage.setTotalNum(EmployeeIPage.getTotal());
        return EmployeeBackPage;
    }
}
