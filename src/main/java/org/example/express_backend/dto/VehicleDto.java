package org.example.express_backend.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VehicleDto {
    private String type;

    private String shift;

    private Integer coordinate;
}
