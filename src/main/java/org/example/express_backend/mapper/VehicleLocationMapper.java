package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.express_backend.entity.VehicleLocation;

@Mapper
public interface VehicleLocationMapper extends BaseMapper<VehicleLocation> {
}
