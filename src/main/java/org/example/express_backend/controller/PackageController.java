package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.express_backend.dto.CreatePackageDTO;
import org.example.express_backend.dto.PackageBatchDTO;
import org.example.express_backend.dto.PackageDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.service.LocationService;
import org.example.express_backend.service.PackageService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/package")
@Api(tags = "包裹管理接口") // 添加了@Api注解，定义了该Controller的描述信息
public class PackageController {
    @Autowired
    private PackageService packageService;
    @Autowired
    private LocationService locationService;

    /**
     * 根据包裹id获取包裹
     * @param id 包裹id
     * @return 查询到的包裹
     */
    @ApiOperation("根据包裹id获取包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @GetMapping("/getPackageById")
    public Result getPackageById(@RequestParam(required = true) Integer id) {
        return Result.ok(packageService.getPackageById(id)).message("获取包裹成功");
    }

    /**
     * 创建包裹
     * @param DTO 创建包裹的信息
     * @return 是否创建成功
     */
    @ApiOperation("创建包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/createPackage")
    public Result createPackage(@RequestBody CreatePackageDTO DTO) {
        if(packageService.createPackage(DTO)){
            return Result.ok().message("创建包裹成功");
        } else {
            return Result.error("创建包裹失败");
        }
    }

    /**
     * 揽收包裹
     * @param packageDTO
     * @return
     */
    @ApiOperation("揽收包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/pickupPackage")
    public Result pickUpPackage(@RequestBody PackageDTO packageDTO){
        if(packageService.pickUpPackage(packageDTO)){
            return Result.ok().message("揽收包裹成功");
        } else {
            return Result.error("揽收包裹失败");
        }
    }

    /**
     * 运输包裹
     * @param packageDTO
     * @return
     */
    @ApiOperation("运输包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/transPackage")
    public Result transPackage(@RequestBody PackageDTO packageDTO){
        if(packageService.transPackage(packageDTO)){
            return Result.ok().message("运输包裹成功");
        } else {
            return Result.error("运输包裹失败");
        }
    }

    /**
     * 派送包裹
     * @param packageDTO
     * @return
     */
    @ApiOperation("派送包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/deliverPackage")
    public Result deliverPackage(@RequestBody PackageDTO packageDTO){
        if(packageService.deliverPackage(packageDTO)){
            return Result.ok().message("派送包裹成功");
        } else {
            return Result.error("派送包裹失败");
        }
    }

    /**
     * 签收包裹
     * @param packageDTO
     * @return
     */
    @ApiOperation("签收包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/signedPackage")
    public Result signedPackage(@RequestBody PackageDTO packageDTO){
        if(packageService.signedPackage(packageDTO)){
            return Result.ok().message("签收包裹成功");
        } else {
            return Result.error("签收包裹失败");
        }
    }

    /**
     * 添加包裹的转运批次ids
     * @param packageBatchDTO
     * @return
     */
    @ApiOperation("添加包裹的转运批次ids") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/addPackageToBatch")
    public Result addPackageToBatch(@RequestBody PackageBatchDTO packageBatchDTO){
        if(packageService.addPackageToBatch(packageBatchDTO)){
            return Result.ok().message("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }

    @GetMapping("/getPackageLocation")
    public Result getPackageLocation(@RequestParam Integer id) {
        return Result.ok(locationService.getPackageLocation(id)).message("获取包裹位置成功");
    }
}
