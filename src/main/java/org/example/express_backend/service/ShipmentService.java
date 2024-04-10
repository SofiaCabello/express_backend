package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreateShipmentDTO;
import org.example.express_backend.dto.ShipmentQueryResultDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;
import org.example.express_backend.mapper.ShipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运单服务类
 */
@Service
public class ShipmentService {
    @Autowired
    private ShipmentMapper shipmentMapper;
    @Lazy // 打破循环依赖
    @Autowired
    private PackageService packageService;

    /**
     * 生成运单号，规则：时间戳后6+出发地网点号
     * @param origin 出发地网点号
     * @return 运单号
     */
    private String generateShipmentId(Integer origin){
        String time = String.valueOf(System.currentTimeMillis());
        return time.substring(time.length() - 6) + origin.toString();
    }

    /**
     * 判断是否同城/同省份：开头两位相同
     * @param origin 出发地
     * @param destination 目的地
     * @return 是否同城/同省份
     */
    public boolean isSameArea(Integer origin, Integer destination){
        return origin.toString().substring(0, 2).equals(destination.toString().substring(0, 2));
    }

    /**
     * 新建运单
     * @param DTO 新建运单信息
     * @return 是否成功
     */
    public boolean createShipment(CreateShipmentDTO DTO){
        Shipment shipment = Shipment.builder()
                .id(Integer.parseInt(generateShipmentId(DTO.getOrigin())))
                .origin(DTO.getOrigin())
                .destination(DTO.getDestination())
                .price(0.0)
                .status("cod_pending".equals(DTO.getPayMethod()) ? Shipment.statusEnum.COD_PENDING.getStatus() : Shipment.statusEnum.PENDING.getStatus())
                .customerId(DTO.getCustomerId())
                .type(DTO.getType())
                .build();
        return shipmentMapper.insert(shipment) == 1;
    }

    /**
     * 根据运单号获取运单
     * @param id 运单号
     * @return 查询到的运单
     */
    public Shipment getShipmentById(Integer id) {
        return shipmentMapper.selectById(id);
    }

    /**
     * 更新价格
     * @param shipmentId 运单号
     * @param price 更新后的价格
     */
    public void updatePrice(Integer shipmentId, Double price) {
        Shipment S = shipmentMapper.selectById(shipmentId);
        if(S == null){
            return;
        }
        try{
            S.setPrice(price);
            shipmentMapper.updateById(S);
        } catch (MybatisPlusException e){
            e.printStackTrace();
        }
    }

    /**
     * 查询运单及运单附属所有包裹信息
     * @param id 运单号
     * @return 查询到的运单及包裹信息
     */
    public ShipmentQueryResultDTO getShipmentWithPackages(Integer id){
        Shipment shipment = getShipmentById(id);
        if(shipment == null){
            return null;
        }
        List<Package> packages = packageService.getPackagesByShipmentId(id);
        return new ShipmentQueryResultDTO(shipment, packages);
    }
}
