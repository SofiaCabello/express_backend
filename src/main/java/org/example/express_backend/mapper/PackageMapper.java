package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.express_backend.entity.Package;

import java.sql.Timestamp;

/**
 * 包裹表 Mapper
 * package(id, sign_date, status, batch_id, receiver_id, receiver_name, receiver_address, receiver_phone)
 */
@Mapper
public interface PackageMapper extends BaseMapper<Package> {
    @Select("SELECT COUNT(*) FROM package WHERE DATE(sign_date) = DATE(#{date})")
    int countPackagesByDate(@Param("date") Timestamp date);
}
