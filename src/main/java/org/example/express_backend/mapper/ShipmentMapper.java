package org.example.express_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.express_backend.entity.Shipment;

/**
 * 运单表 Mapper
 * shipment(id,origin, destination, price, status, customer_id, create_date)
 */
@Mapper
public interface ShipmentMapper extends BaseMapper<Shipment> {
}
