package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.express_backend.entity.Location;

import java.util.List;

@Mapper
public interface LocationMapper extends BaseMapper<Location> {
    @Insert({
            "<script>",
            "insert into location (id, coordinate) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
            "(#{item.id}, #{item.coordinate})",
            "</foreach>",
            "</script>"
    })
    void insertBatch(List<Location> list);
}
