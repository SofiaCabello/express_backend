package org.example.express_backend.controller;


import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.express_backend.dto.VehicleDto;
import org.example.express_backend.entity.Vehicle;
import org.example.express_backend.service.LocationService;
import org.example.express_backend.service.VehicleService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端载具控制器
 * </p>
 *
 * @author Zhiend
 * @since 2024-04-06
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService;

    @Autowired
    private LocationService locationService;

    /**
     * 新增载具接口
     * @param vehicleDto
     * @return
     */
    @ApiOperation("新增载具接口")
    @PostMapping("/save")
    public Result saveVehicle(@RequestBody VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        BeanUtil.copyProperties(vehicleDto, vehicle);
        vehicleService.save(vehicle);
        return Result.ok();
    }

    /**
     * 更新载具接口
     * @param vehicleDto
     * @return
     */
    @ApiOperation("更新载具坐标接口")
    @PostMapping("/updateCoordinate")
    public Result UpdateCoordinate(VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        BeanUtil.copyProperties(vehicleDto, vehicle);
        vehicleService.updateById(vehicle);
        locationService.updatePackageLocation(vehicleDto);
        return Result.ok();
    }


}
