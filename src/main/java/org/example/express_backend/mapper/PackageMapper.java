package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.express_backend.entity.Package;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 包裹表 Mapper
 * package(id, sign_date, status, batch_id, receiver_id, receiver_name, receiver_address, receiver_phone)
 */
@Mapper
public interface PackageMapper extends BaseMapper<Package> {
    @Select("SELECT COUNT(*) FROM package WHERE DATE(sign_date) = DATE(#{date})")
    int countPackagesByDate(@Param("date") Timestamp date);


    /**
     * 统计所有包裹的出发地数量
     * @return List<Map<String, Integer>> 每个Map包含一个出发地的logistic.name和对应的统计数量
     */
    @Select("SELECT l.name AS logistic_name, COUNT(*) AS count " +
            "FROM `package` p " + // 注意这里使用了反引号来避免关键字冲突
            "JOIN shipment s ON p.shipment_id = s.id " +
            "JOIN logistic l ON s.origin = l.id " +
            "GROUP BY l.name " +
            "ORDER BY count DESC")
    List<Map<String, Integer>> getCountsByDeparture();


    /**
     * 统计所有包裹的目的地数量
     * @return List<Map<String, Integer>> 每个Map包含一个目的地的logistic.name和对应的统计数量
     */
    @Select("SELECT l.name AS logistic_name, COUNT(*) AS count " +
            "FROM `package` p " + // 注意这里使用了反引号来避免关键字冲突
            "JOIN shipment s ON p.shipment_id = s.id " +
            "JOIN logistic l ON s.destination = l.id " +
            "GROUP BY l.name " +
            "ORDER BY count DESC")
    List<Map<String, Integer>> getCountsByDestination();


}
