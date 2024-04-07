package org.example.express_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateBatchDTO implements Serializable {
    private Integer origin;
    private Integer destination;
    private Integer responsible;
    private Integer vehicleId;
}
