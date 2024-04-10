package org.example.express_backend.dto;

import lombok.Data;
import org.example.express_backend.entity.Point;

import java.io.Serializable;

@Data
public class LocationResultDTO implements Serializable {
    private String time;
    private Point coordinate;
}
