package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.example.express_backend.dto.CalculatePriceDTO;
import org.example.express_backend.dto.CreatePackageDTO;
import org.example.express_backend.dto.PackageBatchDTO;
import org.example.express_backend.dto.UpdatePackageDTO;
import org.example.express_backend.service.LocationService;
import org.example.express_backend.service.PackageService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/package")
@Api(tags = "包裹管理接口") // 添加了@Api注解，定义了该Controller的描述信息
@CrossOrigin(origins = "*")
public class PackageController {
    @Autowired
    private PackageService packageService;
    @Autowired
    private LocationService locationService;

    /**
     * 计算单个包裹的运费
     * @param calculatePriceDTO 计算运费的信息
     * @return 运费
     */
    @ApiOperation("计算单个包裹的运费") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/calculatePrice")
    public Result<Double> calculatePrice(@RequestBody CalculatePriceDTO calculatePriceDTO){
        return Result.ok(packageService.calculatePrice(calculatePriceDTO)).message("计算成功");
    }

    /**
     * 根据包裹id获取包裹
     * @param id 包裹id
     * @return 查询到的包裹
     */
    @ApiOperation("根据包裹id获取包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @GetMapping("/getPackageById")
    public Result getPackageById(@RequestParam(required = true) Long id) {
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
        try{
            return Result.ok(packageService.createPackage(DTO)).message("创建包裹成功");
        }
        catch (Exception e){
            e.printStackTrace();
            return Result.error("创建包裹失败");
        }
    }

    /**
     * 揽收包裹
     * @param id 包裹id
     * @return 是否揽收成功
     */
    @ApiOperation("揽收包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/pickupPackage")
    public Result pickUpPackage(@RequestBody Long id){
        if(packageService.pickUpPackage(id)){
            return Result.ok().message("揽收包裹成功");
        } else {
            return Result.error("揽收包裹失败");
        }
    }

    /**
     * 更新包裹信息
     */
    @PostMapping("/updatePackage")
    public Result updatePackage(@RequestBody UpdatePackageDTO updatePackageDTO){
        try {
            packageService.updatePackageInfo(updatePackageDTO);
            return Result.ok().message("更新包裹信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("更新包裹信息失败");
        }
    }

    /**
     * 获取未揽收包裹
     * @param logisticsId 网点id
     * @return 未揽收包裹
     */
    @GetMapping("/getUnpickedPackages")
    public Result getUnpickedPackages(@RequestParam Long logisticsId){
        try {
            return Result.ok(packageService.getUnpickedPackages(logisticsId)).message("获取未揽收包裹成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取未揽收包裹失败");
        }
    }

    /**
     * 获取未派送包裹
     * @param logisticsId 网点id
     * @return 未派送包裹
     */
    @GetMapping("/getUndeliveredPackages")
    public Result getUndeliveredPackages(@RequestParam Long logisticsId){
        try {
            return Result.ok(packageService.getUndeliveredPackages(logisticsId)).message("获取未派送包裹成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取未派送包裹失败");
        }
    }

//    /**
//     * 运输包裹
//     * @param packageDTO
//     * @return
//     */
//    @ApiOperation("运输包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
//    @PostMapping("/transPackage")
//    public Result transPackage(@RequestBody PackageDTO packageDTO){
//        if(packageService.transPackage(packageDTO)){
//            return Result.ok().message("运输包裹成功");
//        } else {
//            return Result.error("运输包裹失败");
//        }
//    }

    /**
     * 派送包裹
     * @param id 包裹id
     * @return 是否派送成功
     */
    @ApiOperation("派送包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/deliverPackage")
    public Result deliverPackage(@RequestBody Long id){
        if(packageService.deliverPackage(id)){
            return Result.ok().message("派送包裹成功");
        } else {
            return Result.error("派送包裹失败");
        }
    }

    /**
     * 签收包裹
     * @param id 包裹id
     * @return 是否签收成功
     */
    @ApiOperation("签收包裹") // 添加了@ApiOperation注解，定义了该方法的描述信息
    @PostMapping("/signedPackage")
    public Result signedPackage(@RequestBody Long id){
        if(packageService.signedPackage(id)){
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
        try {
            packageService.addPackageToBatch(packageBatchDTO);
            return Result.ok().message("添加包裹的转运批次ids成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加包裹的转运批次ids失败");
        }
    }

    /**
     * 获取包裹历史
     * @return
     */
    @GetMapping("/getPackageHistory")
    public Result getPackageHistory(@RequestParam Long id){
        try {
            return Result.ok(packageService.getPackageHistory(id)).message("获取成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取失败");
        }
    }

    @ApiOperation("根据出发地统计包裹数量")
    @GetMapping("/getCountsByDeparture")
    public Result<List<Map<String, Integer>>> getCountsByDeparture(){
        return Result.ok(packageService.getCountsByDeparture());
    }

    @ApiOperation("根据出发地统计包裹数量")
    @GetMapping("/getCountsByDestination")
    public Result<List<Map<String, Integer>>> getCountsByDestination(){
        return Result.ok(packageService.getCountsByDestination());
    }

    @GetMapping("/getTotalPackageCount")
    public Result getTotalPackageCount(){
        return Result.ok(packageService.getTotalPackageCount());
    }

    @GetMapping("/getNextDestination")
    public Result getNextDestination(@RequestParam Long packageId, @RequestParam Long currentId){
        return Result.ok(packageService.getNextDestination(packageId, currentId));
    }
}
