package org.example.express_backend.controller;

import org.example.express_backend.service.LogisticService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
