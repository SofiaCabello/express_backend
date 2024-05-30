package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.example.express_backend.entity.Batch;

/**
 * 转运批次表 Mapper 接口
 * batch(id, create_date, origin, destination, responsible, status, vehicle_id)
 */
@Mapper
public interface BatchMapper extends BaseMapper<Batch> {
}
