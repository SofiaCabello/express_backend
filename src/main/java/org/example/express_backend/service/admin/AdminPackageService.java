package org.example.express_backend.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.entity.Package;
import org.example.express_backend.mapper.admin.AdminPackageMapper;
import org.springframework.stereotype.Service;

/**
 * @Classname AdminPackageService
 * @Description TODO
 * @Date 2024/5/16 10:31
 * @Created by Zhiend
 */
@Service
public class AdminPackageService extends ServiceImpl<AdminPackageMapper, Package> implements IService<Package> {

}
