package org.example.express_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.express_backend.entity.Point;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class LocationDTO implements Serializable {
    private Long batchId;
    private Point coordinate;
}
