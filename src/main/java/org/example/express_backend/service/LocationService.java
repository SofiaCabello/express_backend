package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.express_backend.dto.LocationDTO;
import org.example.express_backend.dto.LocationResultDTO;
import org.example.express_backend.dto.VehicleDto;
import org.example.express_backend.entity.Location;
import org.example.express_backend.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 位置信息服务类
 */
@Service
public class LocationService {
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private PackageService packageService;


    /**
     * 插入包裹位置
     * @param locationDTO 位置信息
     */
    public void insertPackageLocation(LocationDTO locationDTO) {
        // 1. 获取批次id，查询包裹id
        Long batchId = locationDTO.getBatchId();
        List<Long> packageIds = packageService.getPackageIdsByBatchId(batchId);
        // 2. 插入位置信息
        List<Location> locations = new ArrayList<>();
        for(Long id : packageIds){
            Location location = Location.builder()
                    .id(id)
                    .coordinate(locationDTO.getCoordinate())
                    .build();
            locations.add(location);
        }
        locationMapper.insertBatch(locations);
    }


    /**
     * 根据id获取包裹位置
     * @param id 包裹id
     * @return 位置信息
     */
    public List<LocationResultDTO> getPackageLocation(Long id) {
        // 1. 根据包裹id查批次id
        Long batchId = packageService.getPackageById(id).getBatchId();
        // 2. 根据批次id查车辆id
        // Long vehicleId =

        return null;
    }

//    /**
//     * 更新包裹列表的位置
//     *
//     * @param vehicleDto 车辆信息
//     */
//    public void updatePackageLocation(VehicleDto vehicleDto){
//        List<Long> packageIds = packageService.getPackageIdsByVehicleId(vehicleDto.getId());
//        List<Location> locations = new ArrayList<>();
//        for(Long id : packageIds){
//            Location location = Location.builder()
//                    .id(id)
//                    .coordinate(vehicleDto.getCoordinate())
//                    .time(new java.sql.Timestamp(System.currentTimeMillis()))
//                    .build();
//            locations.add(location);
//        }
//        insertPackageLocation(locations);
//    }
}
