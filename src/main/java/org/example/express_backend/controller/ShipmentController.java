package org.example.express_backend.controller;

import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreateShipmentDTO;
import org.example.express_backend.service.ShipmentService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    /**
     * 计算单个包裹的运费
     * @param calculatePriceDTO 计算运费的信息
     * @return 运费
     */
    public Result<Double> calculatePrice(CalculatePriceDTO calculatePriceDTO){
        return Result.ok(shipmentService.calculatePrice(calculatePriceDTO)).message("计算成功");
    }

    /**
     * 创建运单
     * @param createShipmentDTO 运单信息
     * @return 是否创建成功
     */
    public Result createShipment(CreateShipmentDTO createShipmentDTO){
        if(shipmentService.createShipment(createShipmentDTO)){
            return Result.ok().message("创建成功");
        }
        return Result.error("创建失败");
    }
}
