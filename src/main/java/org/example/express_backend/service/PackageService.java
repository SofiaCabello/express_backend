package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.example.express_backend.dto.CreatePackageDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

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
}
