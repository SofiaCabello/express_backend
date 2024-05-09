package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.example.express_backend.entity.VehicleLocation;

@Mapper
public interface VehicleLocationMapper extends BaseMapper<VehicleLocation> {
    @Insert("INSERT INTO vehicle_location (vehicle_id, coordinate) VALUES (#{vehicleId}, ST_PointFromText(#{point}, 4326))")
    void insertVehicleLocationWithPoint(@Param("vehicleId") Long vehicleId, @Param("point") String point);
}
