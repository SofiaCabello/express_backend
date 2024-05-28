package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.entity.Location;

import java.util.List;

@Mapper
public interface LocationMapper extends BaseMapper<Location> {
    @Insert("INSERT INTO location (id, coordinate) VALUES (#{id}, ST_PointFromText(#{coordinate}, 4326))")
    void insertLocation(@Param("id") Long id, @Param("coordinate") String coordinate);

    @Select("SELECT ST_AsText(ST_GeomFromWKB(UNHEX(HEX(ST_ASBINARY(coordinate))))) FROM location WHERE id = #{id} order by time desc")
    List<String> selectLocation(@Param("id") Long id);

}
