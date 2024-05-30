package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.express_backend.dto.HistoryDTO;
import org.example.express_backend.entity.Batch;

import java.util.List;

/**
 * 转运批次表 Mapper 接口
 * batch(id, create_date, origin, destination, responsible, status, vehicle_id)
 */
@Mapper
public interface BatchMapper extends BaseMapper<Batch> {
    @Select("SELECT create_date, origin, destination, status FROM batch WHERE JSON_CONTAINS(packages, JSON_OBJECT('id', #{packageId})) order by create_date desc")
    List<HistoryDTO> getBatchByPackageId(Long packageId);
}
