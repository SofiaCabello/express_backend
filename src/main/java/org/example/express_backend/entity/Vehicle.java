package org.example.express_backend.entity;

import lombok.Data;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Data
@ToString
public class Vehicle {
    private Integer id;
    private String type;
    private String shift;
    private Point coordinate; // MySQL: POINT
}
