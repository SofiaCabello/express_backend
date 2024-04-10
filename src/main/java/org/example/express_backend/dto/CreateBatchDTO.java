package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateBatchDTO implements Serializable {
    private Integer origin; // 出发地转运中心编号
    private Integer destination;    // 目的地转运中心编号
    private Integer responsible;    // 负责人id
    private Integer vehicleId;  // 载具id
}
