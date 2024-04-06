package org.example.express_backend.controller;

import org.example.express_backend.dto.CreatePackageDTO;
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
}
