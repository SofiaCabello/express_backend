package org.example.express_backend.dto;

import lombok.Data;
import lombok.ToString;
import org.example.express_backend.entity.Point;

@Data
@ToString
public class VehicleDto {
    private Integer id; // 载具id
    private Point coordinate;   // 坐标
}
