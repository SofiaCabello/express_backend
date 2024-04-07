package org.example.express_backend.controller;

import org.example.express_backend.dto.CreatePackageDTO;
import org.example.express_backend.dto.PackageBatchDTO;
import org.example.express_backend.dto.PackageDTO;
import org.example.express_backend.entity.Package;
import org.example.express_backend.service.PackageService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

    /**
     * 根据包裹id获取包裹，GET方法，请求参数为id
     * @param id 包裹id
     * @return 查询到的包裹
     */
    @GetMapping("/getPackageById")
    public Result getPackageById(@RequestParam(required = true) Integer id) {
        return Result.ok(packageService.getPackageById(id)).message("获取包裹成功");
    }

    /**
     * 创建包裹，POST方法，请求体为CreatePackageDTO
     * @param DTO 创建包裹的信息
     * @return 是否创建成功
     */
    @PostMapping("/createPackage")
    public Result createPackage(@RequestBody CreatePackageDTO DTO) {
        if(packageService.createPackage(DTO)){
            return Result.ok().message("创建包裹成功");
        } else {
            return Result.error("创建包裹失败");
        }
    }

    /**
     * 揽收包裹，对应包裹状态为PROCESSING
     * @param packageDTO
     * @return
     */
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
    @PostMapping("/delieverPackage")
    public Result delieverPackage(@RequestBody PackageDTO packageDTO){
        if(packageService.delieverPackage(packageDTO)){
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
    public Result addPackageToBatch(@RequestBody PackageBatchDTO packageBatchDTO){
        if(packageService.addPackageToBatch(packageBatchDTO)){
            return Result.ok().message("添加成功");
        } else {
            return Result.error("添加失败");
        }
    }


}
