package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.express_backend.entity.Package;

/**
 * 包裹表 Mapper
 * package(id, sign_date, status, batch_id, receiver_id, receiver_name, receiver_address, receiver_phone)
 */
@Mapper
public interface PackageMapper extends BaseMapper<Package> {
}
