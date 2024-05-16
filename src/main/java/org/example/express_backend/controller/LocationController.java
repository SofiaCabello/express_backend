package org.example.express_backend.controller;

import org.example.express_backend.dto.LocationDTO;
import org.example.express_backend.entity.Location;
import org.example.express_backend.service.LocationService;
import org.example.express_backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "*")
public class LocationController {
    @Autowired
    private LocationService locationService;

    /**
     * 插入包裹位置信息
     * @param locationDTO 位置信息
     */
    @PostMapping("/insertPackageLocation")
    public Result insertPackageLocation(@RequestBody LocationDTO locationDTO) {
        try{
            locationService.insertPackageLocation(locationDTO);
            return Result.ok().message("插入包裹位置信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("插入包裹位置信息失败");
        }
    }
}
