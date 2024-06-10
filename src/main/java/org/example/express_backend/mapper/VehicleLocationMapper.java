package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.express_backend.dto.VehicleLocationResultDTO;
import org.example.express_backend.entity.VehicleLocation;

import java.util.List;

@Mapper
public interface VehicleLocationMapper extends BaseMapper<VehicleLocation> {
    @Insert("INSERT INTO vehicle_location (vehicle_id, coordinate) VALUES (#{vehicleId}, ST_PointFromText(#{point}, 4326))")
    void insertVehicleLocationWithPoint(@Param("vehicleId") Long vehicleId, @Param("point") String point);

    @Select("SELECT ST_AsText(ST_GeomFromWKB(UNHEX(HEX(ST_ASBINARY(coordinate))))) as coordinate, created_at FROM vehicle_location WHERE vehicle_id = #{vehicleId} AND created_at >= #{created_at} order by created_at desc")
    List<VehicleLocationResultDTO> getVehicleLocation(@Param("vehicleId") Long vehicleId, @Param("created_at") String created_at);
}
