package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.example.express_backend.dto.CreateShipmentDTO;
import org.example.express_backend.dto.ShipmentQueryResultDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;
import org.example.express_backend.mapper.ShipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private String generateShipmentId(Long origin){
        String time = String.valueOf(System.currentTimeMillis());
        return origin.toString() + time.substring(time.length() - 6);
    }

    /**
     * 判断是否同城/同省份：开头两位相同
     * @param origin 出发地
     * @param destination 目的地
     * @return 是否同城/同省份
     */
    public boolean isSameArea(Long origin, Long destination){
        return origin.toString().substring(0, 2).equals(destination.toString().substring(0, 2));
    }

    /**
     * 新建运单
     * @param DTO 新建运单信息
     * @return 是否成功
     */
    public Long createShipment(CreateShipmentDTO DTO){
        Shipment shipment = Shipment.builder()
                .id(Long.parseLong(generateShipmentId(DTO.getOrigin())))
                .origin(DTO.getOrigin())
                .destination(DTO.getDestination())
                .price(0.0)
                .status("cod_pending".equals(DTO.getPayMethod()) ? Shipment.statusEnum.COD_PENDING.getStatus() : Shipment.statusEnum.PENDING.getStatus())
                .customerId(DTO.getCustomerId())
                .type(DTO.getType())
                // TimeStamp类型
                .createDate(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();
        shipmentMapper.insert(shipment);
        return shipment.getId();
    }

    /**
     * 根据运单号获取运单
     * @param id 运单号
     * @return 查询到的运单
     */
    public Shipment getShipmentById(Long id) {
        return shipmentMapper.selectById(id);
    }

    /**
     * 更新价格
     * @param shipmentId 运单号
     * @param price 更新后的价格
     */
    public void updatePrice(Long shipmentId, Double price) {
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
    public ShipmentQueryResultDTO getShipmentWithPackages(Long id){
        Shipment shipment = getShipmentById(id);
        if(shipment == null){
            return null;
        }
        List<Package> packages = packageService.getPackagesByShipmentId(id);
        return new ShipmentQueryResultDTO(shipment, packages);
    }

    /**
     * 根据用户id获取运单id
     * @param customerId 用户id
     * @return  查询到的运单id
     */
    public List<Long> getShipmentIdsByCustomerId(Long customerId){
        QueryWrapper<Shipment> wrapper = new QueryWrapper<>();
        wrapper.eq("customer_id", customerId);
        return shipmentMapper.selectList(wrapper).stream().map(Shipment::getId).collect(Collectors.toList());
    }

    /**
     * 根据出发地id获取运单id
     */
    public List<Long> getShipmentIdsByOrigin(Long origin) {
        QueryWrapper<Shipment> wrapper = new QueryWrapper<>();
        wrapper.eq("origin", origin);
        return shipmentMapper.selectList(wrapper).stream().map(Shipment::getId).collect(Collectors.toList());
    }

    /**
     * 根据目的地id获取运单id
     */
    public List<Long> getShipmentIdsByDestination(Long destination) {
        QueryWrapper<Shipment> wrapper = new QueryWrapper<>();
        wrapper.eq("destination", destination);
        return shipmentMapper.selectList(wrapper).stream().map(Shipment::getId).collect(Collectors.toList());
    }
}
