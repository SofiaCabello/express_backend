package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateBatchStatusDTO implements Serializable {
    private Integer batchId;
    private String status;
}