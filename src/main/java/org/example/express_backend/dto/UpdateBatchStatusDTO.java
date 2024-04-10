package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateBatchStatusDTO implements Serializable {
    private Long batchId; // 批次id
    private String status;  // 状态
}
