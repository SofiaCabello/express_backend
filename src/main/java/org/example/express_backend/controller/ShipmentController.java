package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreateShipmentDTO;
import org.example.express_backend.service.ShipmentService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
@Api(tags = "运单管理接口") // 添加了@Api注解，定义了该Controller的描述信息
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    /**
     * 计算单个包裹的运费
     * @param calculatePriceDTO 计算运费的信息
     * @return 运费
     */
    @ApiOperation("计算单个包裹的运费") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/calculatePrice")
    public Result<Double> calculatePrice(@RequestBody CalculatePriceDTO calculatePriceDTO){
        return Result.ok(shipmentService.calculatePrice(calculatePriceDTO)).message("计算成功");
    }

    /**
     * 创建运单
     * @param createShipmentDTO 运单信息
     * @return 是否创建成功
     */
    @ApiOperation("创建运单") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/createShipment")
    public Result createShipment(@RequestBody CreateShipmentDTO createShipmentDTO){
        if(shipmentService.createShipment(createShipmentDTO)){
            return Result.ok().message("创建成功");
        }
        return Result.error("创建失败");
    }

    /**
     * 根据运单id获取运单及包裹
     * @param id 运单id
     * @return 查询到的运单
     */
    @GetMapping("/getShipmentWithPackagesById")
    public Result getShipmentWithPackagesById(@RequestParam(required = true) Integer id){
        return Result.ok(shipmentService.getShipmentWithPackages(id)).message("获取成功");
    }
}
