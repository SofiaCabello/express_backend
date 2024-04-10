package org.example.express_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.entity.Vehicle;
import org.example.express_backend.mapper.VehicleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Vehicleservice服务实现类
 * </p>
 *
 * @author Zhiend
 * @since 2024-04-06
 */
@Service
public class VehicleService extends ServiceImpl<VehicleMapper, Vehicle> implements IService<Vehicle> {

}
