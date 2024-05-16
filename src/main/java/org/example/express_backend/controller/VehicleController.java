package org.example.express_backend.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.express_backend.dto.VehicleDto;
import org.example.express_backend.entity.Vehicle;
import org.example.express_backend.service.LocationService;
import org.example.express_backend.service.VehicleService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@CrossOrigin(origins = "*")
public class VehicleController {


    private final VehicleService vehicleService;

    @Autowired
    private LocationService locationService;

    /**
     * 新增载具接口
     * @param vehicle 载具信息
     * @return  新增结果
     */
    @ApiOperation("新增载具接口")
    @PostMapping("/save")
    public Result saveVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.insert(vehicle);
        return Result.ok().message("新增成功");
    }

    /**
     * 更新载具接口
     * @param vehicleDto 载具信息
     * @return 更新结果
     */
    @ApiOperation("更新载具坐标接口")
    @PostMapping("/insertCoordinate")
    public Result insertCoordinate(@RequestBody VehicleDto vehicleDto) {
        try {
            vehicleService.insertVehicleLocation(vehicleDto);
            return Result.ok().message("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新失败");
        }
    }
}
