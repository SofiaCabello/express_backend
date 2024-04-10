package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateBatchDTO implements Serializable {
    private Long origin; // 出发地转运中心编号
    private Long destination;    // 目的地转运中心编号
    private Long responsible;    // 负责人id
    private Long vehicleId;  // 载具id
}
