package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.express_backend.entity.Vehicle;
import org.locationtech.jts.geom.Point;

/**
 * 载具表 Mapper
 * vehicle(id, type, shift, coordinate(GIS point))
 */
@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
    /**
     * 新建载具
     * @param vehicle 载具
     */
    @Insert("insert into vehicle(id, type, shift, coordinate) values(#{id}, #{type}, #{shift}, ST_GeomFromText('POINT(${coordinate.x} ${coordinate.y})'))")
    void createVehicle(Vehicle vehicle);

    /**
     * 更新载具坐标
     * @param id 载具 ID
     * @param coordinate 载具坐标
     */
    @Update("update vehicle set coordinate = ST_GeomFromText('POINT(${coordinate.x} ${coordinate.y})') where id = #{id}")
    void updateCoordinate(Integer id, Point coordinate);
}
