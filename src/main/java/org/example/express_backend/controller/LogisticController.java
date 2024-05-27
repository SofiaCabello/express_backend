package org.example.express_backend.controller;

import io.swagger.annotations.ApiOperation;
import org.example.express_backend.service.LogisticService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/logistic")
public class LogisticController {
    @Autowired
    private LogisticService logisticService;

    @GetMapping("/getTransferRoute")
    public Result getTransferRoute(Long originId, Long destinationId){
        return Result.ok(logisticService.getTransferRoute(originId, destinationId)).message("获取中转路径成功");
    }

    /**
     * 统计网点数量通过province
     * @return
     */
    @ApiOperation("统计各等级网点数据")
    @GetMapping("/countsByLevel")
    public Result<Map<String, int[]>> getCountsByLevel(@RequestParam String level) {
        return Result.ok(logisticService.getCountsByLevel(level));
    }

    @GetMapping("/getLogisticName")
    public Result getLogisticName(@RequestParam Long id){
        return Result.ok(logisticService.getLogisticName(id)).message("获取成功");
    }
}
