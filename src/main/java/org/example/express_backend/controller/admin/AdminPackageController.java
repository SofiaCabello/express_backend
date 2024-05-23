package org.example.express_backend.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.express_backend.service.PackageService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname AdminPackageController
 * @Description 包裹管理表
 * @Date 2024/5/16 10:22
 * @Created by Zhiend
 */
@RestController
@RequestMapping("/adminPac")
@Slf4j
@Api("包裹管理端接口开发")
@CrossOrigin(origins = "*")
public class AdminPackageController {
    @Autowired
    private PackageService packageService;

    @ApiOperation("包裹统计7天的创建数据")
    @GetMapping("/dataBySeven")
    public Result<int []> getDataBySeven() {
        return Result.ok(packageService.getDataBySeven());
    }
}
