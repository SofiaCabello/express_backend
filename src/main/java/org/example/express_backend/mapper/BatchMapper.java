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
    /**
     * 新建转运批次
     * @param batch 转运批次
     */
    @Insert("insert into batch(id, create_date, origin, destination, responsible, status, vehicle_id) values(#{id}, #{createDate}, #{origin}, #{destination}, #{responsible}, #{status}, #{vehicleId})")
    void createBatch(Batch batch);

    /**
     * 更新转运批次状态
     * @param id 批次 ID
     */
    @Update("update batch set status = #{status} where id = #{id}")
    void updateStatus(Integer id, String status);
}
