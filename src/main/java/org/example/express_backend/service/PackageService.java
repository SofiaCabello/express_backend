package org.example.express_backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreatePackageDTO;
import org.example.express_backend.dto.PackageBatchDTO;
import org.example.express_backend.dto.PackageDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;
import org.example.express_backend.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {
    @Autowired
    private PackageMapper packageMapper;
    @Autowired
    private ShipmentService shipmentService;

    /**
     * 根据包裹id获取包裹
     * @param id 包裹id
     * @return 查询到的包裹
     */
    public Package getPackageById(Integer id) {
        return packageMapper.selectById(id);
    }

    /**
     * 添加包裹到运单
     * @param shipmentId 运单id
     * @param packageId 包裹id
     * @return 是否添加成功
     */
    private boolean addPackageToShipment(Integer shipmentId, Integer packageId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", packageId);
        Package P = packageMapper.selectOne(queryWrapper);
        // 如果包裹不存在或者运单不存在
        if(P == null){
            return false;
        }
        // 设定包裹外键
        try {
            P.setShipmentId(shipmentId);
            packageMapper.updateById(P);
        } catch (MybatisPlusException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据运单id获取包裹
     * @param shipmentId 运单id
     * @return 查询到的包裹
     */
    public List<Package> getPackagesByShipmentId(Integer shipmentId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shipment_id", shipmentId);
        return packageMapper.selectList(queryWrapper);
    }

    /**
     * 用来计算运单中单个包裹的运费，注意：不是整个运单的运费（需要计算所有包裹的运费）
     * @param calculatePriceDTO 计算运费的信息
     * @return 运费
     */
    public Double calculatePrice(CalculatePriceDTO calculatePriceDTO){
        // 重量
        Double weight = calculatePriceDTO.getWeight();
        // 尺寸
        String size = calculatePriceDTO.getSize();
        Integer type = calculatePriceDTO.getType();
        Double L = Double.parseDouble(size.split(",")[0]);
        Double W = Double.parseDouble(size.split(",")[1]);
        Double H = Double.parseDouble(size.split(",")[2]);
        Integer origin = calculatePriceDTO.getOrigin();
        Integer destination = calculatePriceDTO.getDestination();
        double volume = L * W * H;
        double VWeight = 0;

        switch (type){
            // 标快
            case 0:
                if(weight < 30){ // 30kg以下
                    VWeight = volume / 12000;
                } else { // 30kg以上
                    VWeight = volume / 6000;
                }
                break;
            // 特快
            case 1:
                if(shipmentService.isSameArea(origin, destination)){ // 同区域
                    VWeight = volume / 12000;
                } else { // 非同区域
                    VWeight = volume / 6000;
                }
        }
        double finalWeight = Math.max(weight, VWeight);
        // 10kg以下，续重以0.1kg为计重单位；10-100kg，续重以0.5kg为计重单位；100kg及以上，四舍五入取整数。
        if(finalWeight <= 10){ // 10kg以下，精确到0.1kg
            finalWeight = Math.ceil(finalWeight * 10) / 10;
        } else if (finalWeight <= 100){ // 10-100kg，精确到0.5kg
            finalWeight = Math.ceil(finalWeight * 2) / 2;
        } else { // 100kg以上，四舍五入取整数
            finalWeight = Math.round(finalWeight);
        }
        return finalWeight * 10; // 10元/kg
    }

    /**
     * 创建包裹
     * @param DTO 创建包裹的信息
     * @return 是否创建成功
     */
    @Transactional // 保证事务的一致性
    public boolean createPackage(CreatePackageDTO DTO) {
        Package P = Package.builder()
                .id(generatePackageId(DTO.getShipmentId()))
                .shipmentId(DTO.getShipmentId())
                .receiverId(DTO.getReceiverId())
                .receiverName(DTO.getReceiverName())
                .receiverAddress(DTO.getReceiverAddress())
                .receiverPhone(DTO.getReceiverPhone())
                .weight(DTO.getWeight())
                .size(DTO.getSize())
                .status(Package.statusEnum.PENDING.getStatus())
                .build();
        Shipment S = shipmentService.getShipmentById(DTO.getShipmentId());
        Double price = S.getPrice() + calculatePrice(new CalculatePriceDTO(S.getOrigin(), S.getDestination(), P.getWeight(), P.getSize(), S.getType()));
        shipmentService.updatePrice(S.getId(), price);
        return packageMapper.insert(P) == 1 && addPackageToShipment(DTO.getShipmentId(), P.getId());
    }

    /**
     * 生成包裹id
     * @param shipmentId 运单id
     * @return 包裹id
     */
    private Integer generatePackageId(Integer shipmentId) {
        // 取6位时间戳
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(7);
        return Integer.parseInt(shipmentId.toString() + timestamp);
    }


    /**
     * 揽收包裹
     * @param dto
     * @return
     */
    public boolean pickUpPackage(PackageDTO dto) {
        return updatePackageStatus(dto, Package.statusEnum.PENDING.getStatus(), Package.statusEnum.PROCESSING.getStatus());
    }

    /**
     * 运输包裹
     * @param packageDTO
     * @return
     */
    public boolean transPackage(PackageDTO packageDTO) {
        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.IN_TRANSIT.getStatus());
    }

    /**
     * 派送包裹
     * @param packageDTO
     * @return
     */
    public boolean deliverPackage(PackageDTO packageDTO) {
        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.DELIVERING.getStatus());
    }

    /**
     * 签收包裹
     * @param packageDTO
     * @return
     */
    public boolean signedPackage(PackageDTO packageDTO) {
        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.SIGNED.getStatus());
    }

/*    *//**
     * 取消包裹
     * @param packageDTO
     * @return
     *//*
    public boolean canceledPackage(PackageDTO packageDTO) {
        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.IN_TRANSIT.getStatus());
    }*/

    /**
     * 更新包裹状态私有方法
     * @param dto
     * @param currentStatus
     * @param newStatus
     * @return
     */
    private boolean updatePackageStatus(PackageDTO dto, String currentStatus, String newStatus) {
        if(!dto.getStatus().equals(currentStatus)){
            return false;
        }
        Package aPackage = new Package();
        BeanUtil.copyProperties(dto, aPackage);
        aPackage.setStatus(newStatus);
        packageMapper.updateById(aPackage);
        return true;
    }

    /**
     * 添加包裹的转运批次ids
     * @param packageBatchDTO
     * @return
     */
    public boolean addPackageToBatch(PackageBatchDTO packageBatchDTO){
        for (Integer id :
                packageBatchDTO.getPackageIds()) {
            Package aPackage = packageMapper.selectById(id);
            aPackage.setBatchId(packageBatchDTO.getBatchId());
        }
        return true;
    }

    public List<Integer> getPackageIdsByVehicleId(Integer vehicleId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vehicle_id", vehicleId);
        List<Package> packages = packageMapper.selectList(queryWrapper);
        return packages.stream().map(Package::getId).collect(Collectors.toList());
    }
}
