package org.example.express_backend.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.entity.BackPage;
import org.example.express_backend.entity.Customer;
import org.example.express_backend.entity.Customer;
import org.example.express_backend.mapper.admin.AdminCustomerMapper;
import org.springframework.stereotype.Service;


/**
 * @Classname AdminCustomerService
 * @Description customer service
 * @Date 2024/5/11 12:06
 * @Created by Zhiend
 */
@Service
public class AdminCustomerService extends ServiceImpl<AdminCustomerMapper, Customer> implements IService<Customer> {
    public BackPage<Customer> queryCustomersPage(Long pageNo, Long pageSize) {
        BackPage<Customer> CustomerBackPage = new BackPage<>();
        // 设置条件构造器
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是第几页，而第二个参数是每页的记录数
        Page<Customer> CustomerPage = new Page<>(pageNo, pageSize);
        // page(CustomerPage, wrapper)这里的第一个参数就是上面定义了的Page对象，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<Customer> CustomerIPage = page(CustomerPage, wrapper);
        // 封装数据，其中getRecords()是获取记录数，getCurrent()获取当前页数，getPages()获取总页数，getTotal()获取记录总数，还要其他更多的方法，大家可以自行查看，在这里就不过多赘述了
        CustomerBackPage.setContentList(CustomerIPage.getRecords());
        CustomerBackPage.setCurrentPage(CustomerIPage.getCurrent());
        CustomerBackPage.setTotalPage(CustomerIPage.getPages());
        CustomerBackPage.setTotalNum(CustomerIPage.getTotal());
        return CustomerBackPage;
    }
}
