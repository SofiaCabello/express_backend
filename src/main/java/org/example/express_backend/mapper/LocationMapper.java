package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import kotlin.Pair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.dto.LocationResultDTO;
import org.example.express_backend.entity.Location;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface LocationMapper extends BaseMapper<Location> {
    @Insert("INSERT INTO location (id, coordinate) VALUES (#{id}, ST_PointFromText(#{coordinate}, 4326))")
    void insertLocation(@Param("id") Long id, @Param("coordinate") String coordinate);

    @Select("SELECT time, ST_AsText(ST_GeomFromWKB(UNHEX(HEX(ST_ASBINARY(coordinate))))) AS location FROM location WHERE id = #{id} order by time desc")
    List<LocationResultDTO> getLocation(@Param("id") Long id);

    @Insert("INSERT INTO location (id, coordinate, time) VALUES (#{id}, ST_PointFromText(#{coordinate} , 4326), #{time})")
    void insertLocationWithTime(@Param("id") Long id, @Param("coordinate") String coordinate, @Param("time") Timestamp time);
}
