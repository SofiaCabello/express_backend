package org.example.express_backend.controller;

import org.example.express_backend.dto.CreateBatchDTO;
import org.example.express_backend.dto.UpdateBatchStatusDTO;
import org.example.express_backend.service.BatchService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchController {
    @Autowired
    private BatchService batchService;

    /**
     * 创建批次
     * @param createBatchDTO 创建批次的信息
     * @return 创建结果
     */
    @PostMapping("/createBatch")
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
    public Result updateBatchStatus(@RequestBody UpdateBatchStatusDTO updateBatchStatusDTO) {
        if (batchService.updateBatchStatus(updateBatchStatusDTO)) {
            return Result.ok().message("更新批次状态成功");
        } else {
            return Result.error("更新批次状态失败");
        }
    }
}
