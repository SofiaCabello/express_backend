package org.example.express_backend.controller;

import org.example.express_backend.service.LocationService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * 获取包裹位置
     * @param id 包裹id
     * @return 包裹位置
     */
    @GetMapping("/getPackageLocation")
    public Result getPackageLocation(@RequestParam Integer id) {
        return Result.ok(locationService.getPackageLocation(id)).message("获取包裹位置成功");
    }
}
