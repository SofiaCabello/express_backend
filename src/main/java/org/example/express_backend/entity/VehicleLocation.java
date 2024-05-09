package org.example.express_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleLocation {
    private Long id;
    private Long vehicleId;
    private Point coordinate;
    private Timestamp createdAt;
}
