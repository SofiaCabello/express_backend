package org.example.express_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.dto.VehicleDto;
import org.example.express_backend.entity.Point;
import org.example.express_backend.entity.Vehicle;
import org.example.express_backend.entity.VehicleLocation;
import org.example.express_backend.mapper.VehicleLocationMapper;
import org.example.express_backend.mapper.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private VehicleLocationMapper vehicleLocationMapper;

    /**
     * 插入载具位置
     * @param vehicleDto 载具位置信息
     */
    public void insertVehicleLocation(VehicleDto vehicleDto) {
        Point point = new Point();
        point = vehicleDto.getCoordinate();
        String pointStr = point.makePoint();
        vehicleLocationMapper.insertVehicleLocationWithPoint(vehicleDto.getId(), pointStr);
    }

    public void insert (Vehicle vehicle) {
        this.save(vehicle);
    }
}
