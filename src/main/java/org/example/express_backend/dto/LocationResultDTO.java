package org.example.express_backend.dto;

import lombok.Data;
import org.bouncycastle.util.Times;
import org.example.express_backend.entity.Point;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class LocationResultDTO implements Serializable {
    private Timestamp time;
    private Point coordinate;
}
