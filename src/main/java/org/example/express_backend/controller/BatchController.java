package org.example.express_backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.express_backend.dto.CreateBatchDTO;
import org.example.express_backend.dto.UpdateBatchStatusDTO;
import org.example.express_backend.service.BatchService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/batch")
@Api(tags = "批次接口服务")
@CrossOrigin(origins = "*")
public class BatchController {
    @Autowired
    private BatchService batchService;

    /**
     * 创建批次
     * @param createBatchDTO 创建批次的信息
     * @return 创建结果
     */
    @PostMapping("/createBatch")
    @ApiOperation("创建批次")
    public Result createBatch(@RequestBody CreateBatchDTO createBatchDTO) {
        if (batchService.createBatch(createBatchDTO)) {
            return Result.ok().message("创建批次成功");
        } else {
            return Result.error("创建批次失败");
        }
    }

    /**
     * 更新批次状态
     * @param updateBatchStatusDTO 更新批次状态的信息
     * @return 更新结果
     */
    @PostMapping("/updateBatchStatus")
    @ApiOperation("更新批次状态")
    public Result updateBatchStatus(@RequestBody UpdateBatchStatusDTO updateBatchStatusDTO) {
        if (batchService.updateBatchStatus(updateBatchStatusDTO)) {
            return Result.ok().message("更新批次状态成功");
        } else {
            return Result.error("更新批次状态失败");
        }
    }

    /**
     * 批次统计7天的创建数据
     * @return 批次统计7天的创建数据
     */
    @ApiOperation("批次统计7天的创建数据")
    @GetMapping("/dataBySeven")
    public Result<int []> getDataBySeven() {
        return Result.ok(batchService.getDataBySeven());
    }
}
