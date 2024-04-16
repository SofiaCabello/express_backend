package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
     * @param locations 位置信息
     */
    private void insertPackageLocation(List<Location> locations){
        for(Location location : locations){
            locationMapper.insert(location);
        }
    }

    /**
     * 根据id获取包裹位置
     * @param id 包裹id
     * @return 位置信息
     */
    public List<LocationResultDTO> getPackageLocation(Long id) {
        QueryWrapper<Location> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        List<Location> locations = locationMapper.selectList(queryWrapper);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return locations.stream().map(location -> {
            LocationResultDTO locationResultDTO = new LocationResultDTO();
            LocalDateTime time = location.getTime().toLocalDateTime();
            locationResultDTO.setTime(time.format(formatter)); // 使用formatter来格式化时间字符串
            locationResultDTO.setCoordinate(location.getCoordinate());
            return locationResultDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 更新包裹列表的位置
     *
     * @param vehicleDto 车辆信息
     */
    public void updatePackageLocation(VehicleDto vehicleDto){
        List<Long> packageIds = packageService.getPackageIdsByVehicleId(vehicleDto.getId());
        List<Location> locations = new ArrayList<>();
        for(Long id : packageIds){
            Location location = Location.builder()
                    .id(id)
                    .coordinate(vehicleDto.getCoordinate())
                    .time(new java.sql.Timestamp(System.currentTimeMillis()))
                    .build();
            locations.add(location);
        }
        insertPackageLocation(locations);
    }
}
