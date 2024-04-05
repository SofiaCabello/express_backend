package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreateShipmentDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;
import org.example.express_backend.mapper.ShipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 运单服务类
 */
@Service
public class ShipmentService {
    @Autowired
    private ShipmentMapper shipmentMapper;
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
                if(isSameArea(origin, destination)){ // 同区域
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
     * 判断是否同城/同省份：开头两位相同
     * @param origin 出发地
     * @param destination 目的地
     * @return 是否同城/同省份
     */
    private boolean isSameArea(Integer origin, Integer destination){
        return origin.toString().substring(0, 2).equals(destination.toString().substring(0, 2));
    }

    /**
     * 新建运单
     * @param DTO 新建运单信息
     * @return 是否成功
     */
    public boolean createShipment(CreateShipmentDTO DTO){
        List<Integer> packageIds = DTO.getPackageIds();
        Double price = 0.0;
        for(Integer packageId: packageIds){
            Package P = packageService.getPackageById(packageId);
            price += calculatePrice(new CalculatePriceDTO(DTO.getOrigin(), DTO.getDestination(), P.getWeight(), P.getSize(), DTO.getType()));
        }
        Shipment shipment = Shipment.builder()
                .id(Integer.parseInt(generateShipmentId(DTO.getOrigin())))
                .origin(DTO.getOrigin())
                .destination(DTO.getDestination())
                .price(price + ("cod_pending".equals(DTO.getPayMethod()) ? 5 : 0)) // 货到付款加5元手续费
                .status("cod_pending".equals(DTO.getPayMethod()) ? Shipment.statusEnum.COD_PENDING.getStatus() : Shipment.statusEnum.PENDING.getStatus())
                .customerId(DTO.getCustomerId())
                .type(DTO.getType())
                .build();
        return shipmentMapper.insert(shipment) == 1;
    }
}
