package org.example.express_backend.dto;

import lombok.Data;
import lombok.ToString;
import org.example.express_backend.entity.Point;

@Data
@ToString
public class VehicleDto {
    private Integer id;

    private Point coordinate;
}
