package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.entity.Logistic;

import java.util.List;

/**
 * 物流网点表 Mapper
 * logistic(id, name, parent_id, level, district, city, province, contact_info)
 */
@Mapper
public interface LogisticMapper extends BaseMapper<Logistic> {
    /**
     * 获取同一父节点下的所有物流网点
     * @param parentId 父节点 ID
     * @return List<Logistic> 物流网点列表
     */
    @Select("SELECT * FROM logistic WHERE parent_id = #{parentId}")
    List<Logistic> getLogisticsByParentId(Integer parentId);

    /**
     * 获取名称对应的父节点 ID
     * @param name 名称
     * @return Integer 父节点 ID
     */
    @Select("SELECT parent_id FROM logistic WHERE name = #{name}")
    Integer getParentIdByName(String name);
}
